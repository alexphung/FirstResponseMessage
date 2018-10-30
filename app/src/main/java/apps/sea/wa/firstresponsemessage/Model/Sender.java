package apps.sea.wa.firstresponsemessage.Model;

public class Sender {
    public Notification notification;
    public String to;
    public UserData data;

    public Sender() {
    }

    public Sender(String to, Notification notification) {
        this.notification = notification;
        this.to = to;
    }

    public Sender(String to, Notification notification, UserData data) {
        this.notification = notification;
        this.to = to;
        this.data = data;
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
