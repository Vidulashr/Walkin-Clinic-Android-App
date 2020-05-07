package com.example.walkinclinic;

import android.database.sqlite.SQLiteDatabase;

import org.junit.Test;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    EmployeeForm employeeForm = new EmployeeForm();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    //This test will be used to make sure my validate name works
    public void validateName(){
        String defaultName = " Omar ";
        //Create an ArrayList of a bunch of invalid name I don't want to allow!
        ArrayList<String> badName = new ArrayList<String>();
        //I want to create a bunch of names that have chars from [0,65) and (90,96) and (122,....)
        for (int i = 0 ; i < 65 ; i++ ){
            //Convert integer to character
            char c = (char)i ;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }
        for (int i = 91 ; i < 96 ; i++ ){
            //Convert integer to character
            char c = (char)i ;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }

        for (int i = 123 ; i < 127 ; i++ ){
            //Convert integer to character
            char c = (char)i ;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }


        //Now I want to send them all through my validate name and make sure none of them come back true
        ArrayList<String> errorFound = new ArrayList<String>();

        for (int i = 0 ; i < badName.size() ; i++ ){
            String currName = badName.get(i);
            if (employeeForm.isValidName(currName)){
                errorFound.add(currName);
            }
        }

        //I just want to validate that errorFound.size() == 0
        assertEquals(0, errorFound.size());
    }





    //This test method will verify if my phone number validation is working
    @Test
    public void validatePhoneNumber(){
        Random rand = new Random();
        //What makes a phone number valid??
        //It should only have number values (0,9)
        // IT SHOULD BE MAX 15 DIGITS!!
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Make an ArrayList with random invalid numbers
        ArrayList<String> invalidPhoneNumber = new ArrayList<String>();
        for (int i = 0 ; i < 100 ; i++ ){
            String randomNumber = "";
           // rand.nextInt((max - min) + 1) + min;
            //I want to make a random size bigger than 15
            int randomPhoneNumSize = rand.nextInt((30-15) + 1) + 15;
            int randomPhoneNumSize2 = rand.nextInt((9-0) + 1);
            if(randomPhoneNumSize%2 == 0 ){
                randomPhoneNumSize = randomPhoneNumSize2;
            }

            for (int j = 0 ; j <= randomPhoneNumSize ; j++ ){
                char letter = abc.charAt(rand.nextInt(abc.length()));
                randomNumber = randomNumber +  j+""+letter;
            }
            invalidPhoneNumber.add(randomNumber);
        }
        ArrayList<String> listOfErrors = new ArrayList<String>();
        for (int i = 0 ; i < invalidPhoneNumber.size() ; i++ ){
            //I want to check if these are acceptable numbers
            String currNumber = invalidPhoneNumber.get(i);
            if(employeeForm.isValidPhoneNum(currNumber)){
                listOfErrors.add(currNumber);

            }
        }

        //I should have listOfErrors.size() == 0
        assertEquals(0, listOfErrors.size());


        ArrayList<String> validPhoneNumber = new ArrayList<String>();

        //I'll now create of proper telephone numbers
        for (int i = 0 ; i < 100 ; i++ ){
            String randomNumber = "";
            int randomPhoneNumSize = rand.nextInt((14-10) + 1) + 10;
            for (int j = 0 ; j <= randomPhoneNumSize ; j++ ){
                int randomNum = rand.nextInt((9-0) + 1);
                randomNumber = randomNumber +randomNum;
                //Add to array
            }
            validPhoneNumber.add(randomNumber);

        }
        //Now I want to make sure there was no errors
        for (int i = 0 ; i < validPhoneNumber.size() ; i++ ){
            String currNumber = validPhoneNumber.get(i);
            if(!employeeForm.isValidPhoneNum(currNumber)){
                listOfErrors.add(currNumber);
                System.out.println(currNumber);
            }

        }

        assertEquals(0, listOfErrors.size());
    }


    @Test
    public void testDuplicateUsernameMethodsDB (){
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~ Now  Testing  Patient  Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );

        String username = "Omar";
        String firstName = "Omar";
        String lastName = "Siage";
        String phoneNumber = "6132121231";
        String address = "441 King George St";
        String email = "Omar.Siage@gmail.com";
        String type = "E";

        User newUser = new Patient(username,firstName,lastName,phoneNumber,address,email,2);


        //I've created an instance of the employee, I now want to test out the methods currently in it

        System.out.println("Before : " + newUser.getFirstName() );
        newUser.setFirstName("Josh");
        assertEquals("Josh", newUser.getFirstName());
        System.out.println("After : " + newUser.getFirstName() );
        System.out.println("Before : " + newUser.getLastName() );
        assertEquals("Patient",newUser.getRole());


        newUser.setLastName("Pedro");
        System.out.println("After : " + newUser.getLastName() );
        assertEquals("Pedro",newUser.getLastName());


        System.out.println("Before : " + newUser.getPhoneNumber());
        newUser.setPhoneNumber("99991231");
        System.out.println("After : " + newUser.getPhoneNumber());
        assertEquals("99991231",newUser.getPhoneNumber());


        System.out.println("Before : " + newUser.getEmail());
        newUser.setEmail("Josh.Pedro232@gmail.com");
        System.out.println("After : " + newUser.getEmail());
        assertEquals("Josh.Pedro232@gmail.com",newUser.getEmail());


        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~ End Of  Testing  Patient  Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );
    }


    @Test
    public void testServiceClass(){
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~  Now Testing Employee Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );

        String username = "Ryan2";
        String firstName = "Ryan";
        String lastName = "Ryan";
        String phoneNumber = "1231231231";
        String address = "441 Ryan St";
        String email = "Ryan.Ryab@gmail.com";
        String type = "E";
        User newUser = new Employee(username,firstName,lastName,phoneNumber,address,email,2);

        //I've created an instance of the employee, I now want to test out the methods currently in it

        System.out.println("Before : " + newUser.getFirstName() );
        //Now let's change the user name
        newUser.setFirstName("Josh");
        //Let's verify name has been actually changed
        //Test #1
        assertEquals("Josh", newUser.getFirstName());
        System.out.println("After : " + newUser.getFirstName() );
        System.out.println("Before : " + newUser.getLastName() );
        assertEquals("Employee",newUser.getRole());


        newUser.setLastName("Pedro");
        System.out.println("After : " + newUser.getLastName() );
        assertEquals("Pedro",newUser.getLastName());


        System.out.println("Before : " + newUser.getPhoneNumber());
        newUser.setPhoneNumber("6132321231");
        System.out.println("After : " + newUser.getPhoneNumber());
        assertEquals("6132321231",newUser.getPhoneNumber());


        System.out.println("Before : " + newUser.getEmail());
        newUser.setEmail("Josh.Pedro@gmail.com");
        System.out.println("After : " + newUser.getEmail());
        assertEquals("Josh.Pedro@gmail.com",newUser.getEmail());

        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~  End Of Testing Employee Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );

    }


    @Test
    //Let us now test the Service class
    public void testService(){
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~ Now Testing Service Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );

        //Create an instance of service
        Service s1 = new Service("Heart", "Doctor", "10000", "2");

        System.out.println("Before : " + s1.getHourlyRate() );
        s1.setHourlyRate("250");
        System.out.println("After : " + s1.getHourlyRate() );
        assertEquals("250",s1.getHourlyRate());


        System.out.println("Before : " + s1.getName() );
        s1.setName("Brain");
        System.out.println("After : " + s1.getName() );
        assertEquals("Brain",s1.getName());

        System.out.println("Before : " + s1.getRole() );
        s1.setRole("Nurse");
        System.out.println("After : " + s1.getRole() );
        assertEquals("Nurse",s1.getRole());

        System.out.println("Before : " + s1.getServiceID() );
        s1.setServiceID("2");
        System.out.println("After : " + s1.getServiceID() );
        assertEquals("2",s1.getServiceID());


        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ " );
        System.out.println("~~~  End Of Testing Service Class ~~~ " );
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~" );






    }














}