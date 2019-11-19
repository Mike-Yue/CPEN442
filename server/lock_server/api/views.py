from django.contrib.auth.models import User
from api.models import Lock, Code
from rest_framework import viewsets
from rest_framework.permissions import IsAuthenticated
from api.serializers import UserSerializer, LockSerializer, CodeSerializer
from api.permissions import IsMasterUserOnly

class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer


class LockViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """

    permission_classes = (IsMasterUserOnly, IsAuthenticated)
    serializer_class = LockSerializer

    def perform_update(self, serializer):
        serializer.save()

    def get_queryset(self):
        user = self.request.user
        return Lock.objects.filter(users__id=user.id)

class CodeViewSet(viewsets.ModelViewSet):
    queryset = Code.objects.all()
    serializer_class = CodeSerializer