package MyDesktopAppMainDirectory.model;

public interface Event {
    public void setPriority(int priority);
    public void setName(int c);
    public String ifStillInProgress();

    //For retrieving index purpose in Db class
    public int getIndex();
}
