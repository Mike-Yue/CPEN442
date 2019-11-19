from rest_framework import permissions
from django.contrib.auth.models import User

class IsMasterUserOnly(permissions.BasePermission):
    """
    Custom permission to only allow owners of an object to see and edit it.
    """

    def has_object_permission(self, request, view, obj):
        # Write permissions are only allowed to the owner of the shopping cart.
        if request.method not in permissions.SAFE_METHODS:
            return str(obj.master_user) == str(request.user)
        else:
            return request.user in obj.users.all()