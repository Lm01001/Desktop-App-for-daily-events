package MyDesktopAppMainDirectory.model;

public interface Event {
    public void setPriority(int priority);
    public void setName(String name);
    public String ifStillInProgress();
}
