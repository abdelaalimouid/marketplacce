package userManagement;

public interface IUser {
  String getUsername();
  boolean isServiceProvider();
  boolean isClient();
}