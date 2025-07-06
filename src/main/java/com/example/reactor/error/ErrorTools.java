package com.example.reactor.error;

public interface ErrorTools {
    public static void logErrorData(Exception e, String location){
        System.out.println(
            "############# error in "+ location +" #############" + "\n" +
            "message : " + e.getMessage() + "\n" +
            "cause : " + e.getCause() + "\n" +
            "hash code : " + e.hashCode() + "\n" +
            "localized message : " + e.getLocalizedMessage() + "\n" +
            "class : " + e.getClass() + "\n" +
            "############# end of error details ############"
        );
    } 
}
