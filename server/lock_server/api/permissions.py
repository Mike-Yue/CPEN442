from rest_framework import permissions
from django.contrib.auth.models import User
from api.models import Code, Lock

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

class CodePermission(permissions.BasePermission):

    def has_permission(self, request, view):
        if request.method == "GET":
            user = request.user
            if 'lock_id' not in request.query_params.keys() or 'code' not in request.query_params.keys():
                return False
            lock_id = request.query_params['lock_id']
            entry_code = request.query_params['code']
            try:
                target_code = Code.objects.get(code=int(entry_code))
                target_lock = Lock.objects.get(lock_id=lock_id)
            except:
                return False
            if user in target_lock.users.all() and target_code.lock == target_lock:
                return True
            else:
                return False