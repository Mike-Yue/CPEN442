from django.contrib.auth.models import User, Group
from rest_framework import serializers
from api.models import Lock, Code

class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ['url', 'username', 'email', 'groups']


class LockSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Lock
        fields = "__all__"

class CodeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Code
        fields = "__all__"