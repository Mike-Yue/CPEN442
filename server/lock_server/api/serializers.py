from django.contrib.auth.models import User, Group
from rest_framework import serializers
from api.models import Lock, Code

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username', 'email']


class LockSerializer(serializers.ModelSerializer):
    master_user = UserSerializer(read_only=True)
    users = UserSerializer(many=True)
    class Meta:
        model = Lock
        fields = "__all__"

class CodeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Code
        fields = "__all__"