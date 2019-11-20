from django.contrib.auth.models import User
from django.http import HttpResponse
from api.models import Lock, Code
from rest_framework import viewsets
from rest_framework.permissions import IsAuthenticated
from api.serializers import UserSerializerRead, UserSerializerWrite, LockSerializer, LockSerializerCreate, CodeSerializer
from api.permissions import IsMasterUserOnly, CreateUserAllowed

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
    queryset = Code.objects.all()
    serializer_class = CodeSerializer
