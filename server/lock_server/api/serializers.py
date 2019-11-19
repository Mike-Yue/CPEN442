from django.contrib.auth.models import User, Group
from rest_framework import serializers
from api.models import Lock, Code

class UserSerializerWrite(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ["username", "email", "password"]

class UserSerializerRead(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ["id", "username", "email"]

class LockSerializer(serializers.ModelSerializer):
    master_user = UserSerializerRead(read_only=True)
    users = UserSerializerRead(many=True, read_only=True)
    class Meta:
        model = Lock
        fields = "__all__"

class LockSerializerCreate(serializers.ModelSerializer):
    class Meta:
        model = Lock
        fields = "__all__"


class CodeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Code
        fields = "__all__"