package org.example;

public class BeachUtilitiesData {
    private int loungeFor2People;
    private int loungeFor3People;
    private int loungeFor4People;

    public BeachUtilitiesData(int loungeFor2People, int loungeFor3People, int loungeFor4People) {
        this.loungeFor2People = loungeFor2People;
        this.loungeFor3People = loungeFor3People;
        this.loungeFor4People = loungeFor4People;
    }

    public int getLoungeFor2Quantity() {
        return this.loungeFor2People;
    }

    public int getLoungeFor3Quantity() {
        return this.loungeFor3People;
    }

    public int getLoungeFor4Quantity() {
        return this.loungeFor4People;
    }

    // Returns whether booking was successful
    public boolean bookLounges(int numberOfLoungeFor2, int numberOfLoungeFor3, int numberOfLoungeFor4) {
        if (this.loungeFor2People < numberOfLoungeFor2 || this.loungeFor3People < numberOfLoungeFor3 || this.loungeFor4People < numberOfLoungeFor4) {
            return false;
        }

        this.loungeFor2People -= numberOfLoungeFor2;
        this.loungeFor3People -= numberOfLoungeFor3;
        this.loungeFor4People -= numberOfLoungeFor4;
        return true;
    }
}
