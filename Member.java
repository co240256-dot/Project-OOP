// No package declaration to keep compilation simple for this assignment

/**
 * Represents a registered library member. Each member has a unique identifier
 * and a human‑readable name. This class is intentionally simple – all logic
 * related to managing members (adding, searching, etc.) is delegated to the
 * Library class to keep responsibilities separated.
 */
public class Member {
    private String memberID;
    private String name;

    /**
     * Creates a new Member with the given id and name.
     *
     * @param memberID the unique identifier for this member
     * @param name     the member’s full name
     */
    public Member(String memberID, String name) {
        this.memberID = memberID;
        this.name = name;
    }

    /**
     * Returns this member’s unique identifier.
     */
    public String getMemberID() {
        return memberID;
    }

    /**
     * Updates this member’s identifier. Use with caution, as ids should be
     * unique.
     */
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    /**
     * Returns the name of this member.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates this member’s name.
     */
    public void setName(String name) {
        this.name = name;
    }
}