from django.db import models
from django_filters import rest_framework as filters

from api.models import Code


class CodeFilter(filters.FilterSet):

	class Meta:
		model = Code
		fields = ["id", "lock"]
