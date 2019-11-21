from django.urls import include, path
from rest_framework import routers
from api import views
from django.contrib import admin
from rest_framework.authtoken import views as auth_views

router = routers.DefaultRouter()
router.register(r'users', views.UserViewSet, base_name="users")
router.register(r'locks', views.LockViewSet, base_name="locks")
router.register(r'codes', views.CodeViewSet, base_name="codes")

# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path(r'login', auth_views.obtain_auth_token),
    path('validate/', views.validate),
]