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
from rest_framework.decorators import api_view, permission_classes
import secrets

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

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def createCode(request):
    try:
        target_lock = Lock.objects.get(lock_id=request.data['lock_id'])
        print(target_lock)
        if request.user in target_lock.users.all():
            #Need to implement custom expiry time
            code_generator = secrets.SystemRandom()
            code = code_generator.randint(0,9999)
            print(code)
            temp = Code.objects.create(code=code, lock=target_lock)
            print(temp)
            return Response({"Message": "Your code is: {}".format(str(code).zfill(4))}, status=status.HTTP_200_OK)
    except:
        return Response({"Error": "Forbidden"}, status=status.HTTP_403_FORBIDDEN)

@api_view(['GET', 'POST'])
def validate(request):
    if request == "GET":
        return Response({"message": "Hello, world!"})
    else:
        lock_id = request.data['lock_id']
        entry_code = request.data['code']
        try:
            target_code = Code.objects.get(code=int(entry_code))
            target_lock = Lock.objects.get(lock_id=lock_id)
        except:
            return Response({"Error": "Forbidden"}, status=status.HTTP_403_FORBIDDEN)
        if target_code.lock == target_lock:
            target_code.delete()
            return Response({"Message": "Code is valid"}, status=status.HTTP_200_OK)
        return Response({"Error": "Forbidden"}, status=status.HTTP_403_FORBIDDEN)