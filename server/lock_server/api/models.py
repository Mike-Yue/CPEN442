from django.db import models
from django.contrib.auth.models import User
class Lock(models.Model):

    lock_id = models.CharField(
        max_length=120,
        unique=True,
        null=False,
    )

    users = models.ManyToManyField(
        User,
        blank=True
    )


class Code(models.Model):

    code = models.IntegerField()

    lock = models.ForeignKey(
        Lock,
        on_delete=models.CASCADE,
        null=False,
    )

    expiry_time = models.DateTimeField()

    expired = models.BooleanField(
        default=False
    )