from django.db import models
from django.contrib.auth.models import User
class Lock(models.Model):

    def __str__(self):
        return self.lock_id

    lock_id = models.CharField(
        max_length=120,
        unique=True,
        null=False,
    )

    master_user = models.ForeignKey(
        User,
        related_name="master_user",
        on_delete=models.CASCADE,
        null=True,
    )

    users = models.ManyToManyField(
        User,
        related_name="users",
        blank=True
    ) 


class Code(models.Model):

    def __str__(self):
        return str(self.code)

    code = models.IntegerField()

    lock = models.ForeignKey(
        Lock,
        on_delete=models.CASCADE,
        null=False,
    )

    expiry_time = models.DateTimeField(
        null=True,
    )