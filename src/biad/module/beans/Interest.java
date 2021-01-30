package biad.module.beans;

public class Interest {
    private String objectOfInterest;
    private InterestType interestType;

    public Interest(String objectOfInterest, InterestType interestType) {
        this.objectOfInterest = objectOfInterest;
        this.interestType = interestType;
    }

    public boolean isObjectOfInterest(String thing) {
        return thing.equals(objectOfInterest);
    }

    public boolean isObjectOfInterest(Book book) {
        if (interestType == InterestType.INTEREST_IN_AUTHOR){
            return book.getAuthor().equals(objectOfInterest);
        }
        if (interestType == InterestType.INTEREST_IN_SUBJECT){
            return book.getSubject().toString().equals(objectOfInterest);
        }
        if (interestType == InterestType.INTEREST_IN_TITLE){
            return book.getBookTitle().equals(objectOfInterest);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Interest)) return false;
        Interest interest = (Interest) obj;
        return (this.objectOfInterest.equals(interest.objectOfInterest) &&
                this.interestType.equals(interest.interestType));
    }

    @Override
    public String toString() {
        return "Interest{" +
                "objectOfInterest='" + objectOfInterest + '\'' +
                ", interestType=" + interestType +
                '}';
    }
}
