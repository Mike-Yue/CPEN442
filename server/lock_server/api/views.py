from django.contrib.auth.models import User
from django.http import HttpResponseForbidden, HttpResponse
from api.models import Lock, Code
from rest_framework import viewsets
from rest_framework.permissions import IsAuthenticated
from api.serializers import UserSerializerRead, UserSerializerWrite, LockSerializer, LockSerializerCreate, CodeSerializer
from api.permissions import IsMasterUserOnly, CodePermission
from django_filters import rest_framework as filters
from api.filters import CodeFilter
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """

    def get_queryset(self):
        user = self.request.user
        return User.objects.filter(id=user.id)

    def get_serializer_class(self):
        if self.action in ['create', 'update', 'partial_update']:
            return UserSerializerWrite
        return UserSerializerRead

class LockViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """

    permission_classes = (IsMasterUserOnly, IsAuthenticated)
    
    def get_serializer_class(self):
        if self.action in ['create', 'update', 'partial_update']:
            return LockSerializerCreate
        return LockSerializer

    def perform_update(self, serializer):
        serializer.save()

    def get_queryset(self):
        user = self.request.user
        return Lock.objects.filter(users__id=user.id)

class CodeViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, CodePermission)
    serializer_class = CodeSerializer
    filter_class = CodeFilter

    def get_queryset(self):
        user = self.request.user
        lock_id = self.request.query_params['lock_id']
        entry_code = self.request.query_params['code']
        target_code = Code.objects.get(code=int(entry_code))
        target_lock = Lock.objects.get(lock_id=lock_id)
        if user in target_lock.users.all() and target_code.lock == target_lock:
            return Code.objects.filter(id=target_code.id)
        else:
            return Code.objects.none()


@api_view(['GET', 'POST'])
def validate(request):
    if request == "GET":
        return Response({"message": "Hello, world!"})
    else:
        user = request.user
        lock_id = request.data['lock_id']
        entry_code = request.data['code']
        try:
            target_code = Code.objects.get(code=int(entry_code))
            print(lock_id, entry_code)
            target_lock = Lock.objects.get(lock_id=lock_id)
        except:
            return Response({"Error": "Forbidden"}, status=status.HTTP_403_FORBIDDEN)
        if user in target_lock.users.all() and target_code.lock == target_lock:
            return Response({"Message": "Code is valid"}, status=status.HTTP_200_OK)
        return Response({"Error": "Forbidden"}, status=status.HTTP_403_FORBIDDEN)