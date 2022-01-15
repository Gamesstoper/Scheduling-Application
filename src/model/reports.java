package model;

public class reports {
    private String months;
    private int planning;
    private int brief;
    private int personal;
    private int closeAccount;
    private String createdBy;
    private String createDate;
    private int apptID;
    private String contact;

    /**reports.
     * @param month
     * @param brief
     * @param closeAccount
     * @param personal
     * @param planning
     * This creates our main constructor for formatting and setting up our reports objects */
    public reports(String month, int planning, int brief, int personal, int closeAccount){
        setMonths(month);
        setPlanning(planning);
        setBrief(brief);
        setPersonal(personal);
        setCloseAccount(closeAccount);
    }

    /**reports.
     * @param contact
     * @param apptID
     * @param createDate
     * @param createdBy
     * This creates another constructor for formatting and setting up our reports objects */
    public reports(String createdBy, String createDate, int apptID,String contact){
        setCreatedBy(createdBy);
        setCreateDate(createDate);
        setApptID(apptID);
        setContact(contact);
    }

    /**getMonths.
     * @return
     * Standard getter*/
    public String getMonths() {
        return months;
    }

    /**setMonths.
     * @param months
     * Standard setter*/
    public void setMonths(String months) {
        this.months = months;
    }

    /**getPlanning.
     * @return
     * Standard getter*/
    public int getPlanning() {
        return planning;
    }

    /**setPlanning.
     * @param planning
     * Standard setter*/
    public void setPlanning(int planning) {
        this.planning = planning;
    }

    /**getBrief.
     * @return
     * Standard getter*/
    public int getBrief() {
        return brief;
    }

    /**setBrief.
     * @param brief
     * Standard setter*/
    public void setBrief(int brief) {
        this.brief = brief;
    }

    /**getPersonal.
     * @return
     * Standard getter*/
    public int getPersonal() {
        return personal;
    }

    /**setPersonal.
     * @param personal
     * Standard setter*/
    public void setPersonal(int personal) {
        this.personal = personal;
    }

    /**getCloseAccount.
     * @return
     * Standard getter*/
    public int getCloseAccount() {
        return closeAccount;
    }

    /**setCloseAccount.
     * @param closeAccount
     * Standard setter*/
    public void setCloseAccount(int closeAccount) {
        this.closeAccount = closeAccount;
    }

    /**getCreatedBy.
     * @return
     * Standard getter*/
    public String getCreatedBy() {
        return createdBy;
    }

    /**setCreatedBy.
     * @param createdBy
     * Standard setter*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**getCreateDate.
     * @return
     * Standard getter*/
    public String getCreateDate() {
        return createDate;
    }

    /**setCreateDate.
     * @param createDate
     * Standard setter*/
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**getApptID.
     * @return
     * Standard getter*/
    public int getApptID() {
        return apptID;
    }

    /**setApptID.
     * @param apptID
     * Standard setter*/
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /**getContact.
     * @return
     * Standard getter*/
    public String getContact() {
        return contact;
    }

    /**setContact.
     * @param contact
     * Standard setter*/
    public void setContact(String contact) {
        this.contact = contact;
    }
}
