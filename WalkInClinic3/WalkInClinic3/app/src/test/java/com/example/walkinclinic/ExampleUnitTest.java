package com.example.walkinclinic;

import android.database.sqlite.SQLiteDatabase;
import android.util.Patterns;

import org.junit.Test;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;



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
    public void validateNameEmployee() {
        String defaultName = " Omar ";
        //Create an ArrayList of a bunch of invalid name I don't want to allow!
        ArrayList<String> badName = new ArrayList<String>();
        //I want to create a bunch of names that have chars from [0,65) and (90,96) and (122,....)
        for (int i = 0; i < 65; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }
        for (int i = 91; i < 96; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }

        for (int i = 123; i < 127; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }


        //Now I want to send them all through my validate name and make sure none of them come back true
        ArrayList<String> errorFound = new ArrayList<String>();

        for (int i = 0; i < badName.size(); i++) {
            String currName = badName.get(i);
            if (employeeForm.isValidName(currName)) {
                errorFound.add(currName);
            }
        }

        //I just want to validate that errorFound.size() == 0
        assertEquals(0, errorFound.size());
    }


    //This test method will verify if my phone number validation is working
    @Test
    public void validatePhoneNumberEmployee() {
        Random rand = new Random();
        //What makes a phone number valid??
        //It should only have number values (0,9)
        // IT SHOULD BE MAX 15 DIGITS!!
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Make an ArrayList with random invalid numbers
        ArrayList<String> invalidPhoneNumber = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            String randomNumber = "";
            // rand.nextInt((max - min) + 1) + min;
            //I want to make a random size bigger than 15
            int randomPhoneNumSize = rand.nextInt((30 - 15) + 1) + 15;
            int randomPhoneNumSize2 = rand.nextInt((9 - 0) + 1);
            if (randomPhoneNumSize % 2 == 0) {
                randomPhoneNumSize = randomPhoneNumSize2;
            }

            for (int j = 0; j <= randomPhoneNumSize; j++) {
                char letter = abc.charAt(rand.nextInt(abc.length()));
                randomNumber = randomNumber + j + "" + letter;
            }
            invalidPhoneNumber.add(randomNumber);
        }
        ArrayList<String> listOfErrors = new ArrayList<String>();
        for (int i = 0; i < invalidPhoneNumber.size(); i++) {
            //I want to check if these are acceptable numbers
            String currNumber = invalidPhoneNumber.get(i);
            if (employeeForm.isValidPhoneNum(currNumber)) {
                listOfErrors.add(currNumber);

            }
        }

        //I should have listOfErrors.size() == 0
        assertEquals(0, listOfErrors.size());


        ArrayList<String> validPhoneNumber = new ArrayList<String>();

        //I'll now create of proper telephone numbers
        for (int i = 0; i < 100; i++) {
            String randomNumber = "";
            int randomPhoneNumSize = rand.nextInt((14 - 10) + 1) + 10;
            for (int j = 0; j <= randomPhoneNumSize; j++) {
                int randomNum = rand.nextInt((9 - 0) + 1);
                randomNumber = randomNumber + randomNum;
                //Add to array
            }
            validPhoneNumber.add(randomNumber);

        }
        //Now I want to make sure there was no errors
        for (int i = 0; i < validPhoneNumber.size(); i++) {
            String currNumber = validPhoneNumber.get(i);
            if (!employeeForm.isValidPhoneNum(currNumber)) {
                listOfErrors.add(currNumber);
                System.out.println(currNumber);
            }

        }

        assertEquals(0, listOfErrors.size());
    }


    @Test
    public void testDuplicateUsernameMethodsDB() {
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~ Now  Testing  Patient  Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");

        String username = "Omar";
        String firstName = "Omar";
        String lastName = "Siage";
        String phoneNumber = "6132121231";
        String address = "441 King George St";
        String email = "Omar.Siage@gmail.com";
        String type = "E";

        User newUser = new Patient(username, firstName, lastName, phoneNumber, address, email, 2);


        //I've created an instance of the employee, I now want to test out the methods currently in it

        System.out.println("Before : " + newUser.getFirstName());
        newUser.setFirstName("Josh");
        assertEquals("Josh", newUser.getFirstName());
        System.out.println("After : " + newUser.getFirstName());
        System.out.println("Before : " + newUser.getLastName());
        assertEquals("Patient", newUser.getRole());


        newUser.setLastName("Pedro");
        System.out.println("After : " + newUser.getLastName());
        assertEquals("Pedro", newUser.getLastName());


        System.out.println("Before : " + newUser.getPhoneNumber());
        newUser.setPhoneNumber("99991231");
        System.out.println("After : " + newUser.getPhoneNumber());
        assertEquals("99991231", newUser.getPhoneNumber());


        System.out.println("Before : " + newUser.getEmail());
        newUser.setEmail("Josh.Pedro232@gmail.com");
        System.out.println("After : " + newUser.getEmail());
        assertEquals("Josh.Pedro232@gmail.com", newUser.getEmail());


        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~ End Of  Testing  Patient  Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");
    }


    @Test
    public void testServiceClass() {
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~  Now Testing Employee Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");

        String username = "Ryan2";
        String firstName = "Ryan";
        String lastName = "Ryan";
        String phoneNumber = "1231231231";
        String address = "441 Ryan St";
        String email = "Ryan.Ryab@gmail.com";
        String type = "E";
        User newUser = new Employee(username, firstName, lastName, phoneNumber, address, email, 2);

        //I've created an instance of the employee, I now want to test out the methods currently in it

        System.out.println("Before : " + newUser.getFirstName());
        //Now let's change the user name
        newUser.setFirstName("Josh");
        //Let's verify name has been actually changed
        //Test #1
        assertEquals("Josh", newUser.getFirstName());
        System.out.println("After : " + newUser.getFirstName());
        System.out.println("Before : " + newUser.getLastName());
        assertEquals("Employee", newUser.getRole());


        newUser.setLastName("Pedro");
        System.out.println("After : " + newUser.getLastName());
        assertEquals("Pedro", newUser.getLastName());


        System.out.println("Before : " + newUser.getPhoneNumber());
        newUser.setPhoneNumber("6132321231");
        System.out.println("After : " + newUser.getPhoneNumber());
        assertEquals("6132321231", newUser.getPhoneNumber());


        System.out.println("Before : " + newUser.getEmail());
        newUser.setEmail("Josh.Pedro@gmail.com");
        System.out.println("After : " + newUser.getEmail());
        assertEquals("Josh.Pedro@gmail.com", newUser.getEmail());

        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~  End Of Testing Employee Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");

    }


    @Test
    //Let us now test the Service class
    public void testService() {
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~ Now Testing Service Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");

        //Create an instance of service
        Service s1 = new Service("Heart", "Doctor", "10000", "2");

        System.out.println("Before : " + s1.getHourlyRate());
        s1.setHourlyRate("250");
        System.out.println("After : " + s1.getHourlyRate());
        assertEquals("250", s1.getHourlyRate());


        System.out.println("Before : " + s1.getName());
        s1.setName("Brain");
        System.out.println("After : " + s1.getName());
        assertEquals("Brain", s1.getName());

        System.out.println("Before : " + s1.getRole());
        s1.setRole("Nurse");
        System.out.println("After : " + s1.getRole());
        assertEquals("Nurse", s1.getRole());

        System.out.println("Before : " + s1.getServiceID());
        s1.setServiceID("2");
        System.out.println("After : " + s1.getServiceID());
        assertEquals("2", s1.getServiceID());


        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~ ");
        System.out.println("~~~  End Of Testing Service Class ~~~ ");
        System.out.println("~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~");


    }


    //Test method --> Valid Role
    //In our applicaton, we only allow the following words to be entered :
    //Doctor, Nurse & Staff

    //DOCTOR, DocTor, doctor should be accepted

    @Test
    public void testValidRole() {
        //These should all work
        ArrayList<String> shouldWork = new ArrayList<String>();
        shouldWork.add("DOCTOR");
        shouldWork.add("dOCtor");
        shouldWork.add("dOCTOR");
        shouldWork.add("dOCtOR");
        shouldWork.add("NurSe");
        shouldWork.add("NURSE");
        shouldWork.add("nurse");
        shouldWork.add("NuRsE");
        shouldWork.add("sTaFf");
        shouldWork.add("STAFF");
        shouldWork.add("staff");
        //Instantiate an object from ServiceForm

        ServiceForm sv = new ServiceForm();

        ArrayList<String> listOfErrors = new ArrayList<String>();
        //For loop and test every String that I know should work
        for (int i = 0; i < shouldWork.size(); i++) {
            //If the return value is values, add that string in my list of errors
            if (!sv.validRole(shouldWork.get(i))) {
                listOfErrors.add(shouldWork.get(i));
            }
        }

        assertEquals(0, listOfErrors.size());


        //Let us make a list of words that should definetely fail
        ArrayList<String> shouldNOTwork = new ArrayList<String>();
        shouldWork.add("doc");
        shouldWork.add("seal");
        shouldWork.add("Toilasd");
        shouldWork.add("12312asd");
        shouldWork.add("asd");
        shouldWork.add("loasdc");
        shouldWork.add("z");
        shouldWork.add("123");
        shouldWork.add("qw");
        shouldWork.add("12asSta11");
        shouldWork.add("1");

        ArrayList<String> listOfErrors2 = new ArrayList<String>();
        //For loop and test every String that I know should work
        for (int i = 0; i < shouldNOTwork.size(); i++) {
            //If the return value is values, add that string in my list of errors
            if (sv.validRole(shouldNOTwork.get(i))) {
                listOfErrors2.add(shouldWork.get(i));
            }
        }

        assertEquals(0, listOfErrors2.size());


    }

    //Here's the following cases that could occur
    //A valid rate must be an integer or a double/long with at least 2 numbers after the decimal
    //Giving a number with a letter such as --> 123b12312 --> Should be false
    //Giving a string --> asdjasd --> Should be false
    //Giving an integer --> 10 --> should be true
    //Giving a double or a float such as --> 10.23 should be true
    //Giving a double or a float such as --> 20.123 should be false. We can't have more than 2 numbers after decimal
    @Test

    public void validRate() {
        //Instantiate an instance of ServiceForm, so we can the validRate() method
        ServiceForm sv = new ServiceForm();
        //Let us trying sending a number with a letter
        assertFalse(sv.validRate("1237b1231"));
        //Let us now try with a string containing all letters
        assertFalse(sv.validRate("asdasdasd"));
        //Let us now try with a string containing an integer value
        assertTrue(sv.validRate("10"));
        //let us try with only 2 decimal numbers
        assertTrue(sv.validRate("10.23"));
        //let us now try with more than 3 decimal numbers
        assertFalse(sv.validRate("10.232"));
    }


    PatientForm patientForm = new PatientForm();

    //Tests for password strength -> passStrength()
    //Ensures users make a pass that is more than 8 characters
    //with 1 upper,1 lower, 1 digit and 1 special character

    @Test

    public void passStrength() {
        Random random = new Random();
        ArrayList<String> listOfshortpass = new ArrayList<String>();
        //creating lists of short passwords that wont be strong
        for (int i = 0; i < 10; i++) {
            String pass = "";
            for (int x = 0; x < 7; x++) {
                pass += String.valueOf(random.nextInt());
            }
            listOfshortpass.add(pass);
        }

        //Made a list of pass that are long enough ut dont contain all the types of characters
        ArrayList<String> listOfweakpass = new ArrayList<String>();
        listOfweakpass.add("aaaaaaaa"); //just lower
        listOfweakpass.add("11111111"); //just digits
        listOfweakpass.add("AAAAAAAA"); //just upper
        listOfweakpass.add("@@@@@@@@"); //just special
        listOfweakpass.add("aaaaAAAA"); //upper and lower
        listOfweakpass.add("aaaa@@@@"); //lower and special
        listOfweakpass.add("AAAA@@@@"); //upper and special
        listOfweakpass.add("aaaa1111"); //lower and digit
        listOfweakpass.add("AAAA1111"); //upper and digit
        listOfweakpass.add("@@@@1111"); //special and digit
        listOfweakpass.add("aaaa11AA"); //upper lower and digit
        listOfweakpass.add("AAAA11@@"); //upper digit and special
        listOfweakpass.add("aaAA@@@@"); //lower digit and special


        //Now I want to send them all through pass strength validate and make sure none of them come back true
        ArrayList<String> errorFound = new ArrayList<String>();

        //for list of short passwords
        for (int i = 0; i < listOfshortpass.size(); i++) {
            String currPass = listOfshortpass.get(i);
            if (patientForm.passStrength(currPass)) {
                errorFound.add(currPass);
            }
        }

        //for list of weak passwords
        for (int i = 0; i < listOfweakpass.size(); i++) {
            String currPass = listOfweakpass.get(i);
            if (patientForm.passStrength(currPass)) {
                errorFound.add(currPass);
            }
        }

        //I just want to validate that errorFound.size() == 0
        assertEquals(0, errorFound.size());
    }

    ClinicHours clinichours = new ClinicHours();

    //Tests for valid hours inputted in clinics -> validatedHours()
    //Ensures clinics do not input hours that dont make sense
    //cannot go from PM to AM

    @Test

    public void validateHours() {
        Random randomhours = new Random();

        ArrayList<String> errorFound = new ArrayList<String>();

        ArrayList<String> hours = new ArrayList<String>();
        hours.add("1");
        hours.add("2");
        hours.add("3");
        hours.add("4");
        hours.add("5");
        hours.add("6");
        hours.add("7");
        hours.add("8");
        hours.add("9");
        hours.add("10");
        hours.add("11");
        hours.add("12");

        //when both AP is AM is the same, hour cant be more after
        for (int i = 0; i < 12; i++) {
            if (clinichours.validateHour(hours.get(11), "00", "AM", hours.get(i), "00", "AM")) { //AM in both before and after
                errorFound.add("error");
            }
        }

        //when both AP is PM is the same, hour cant be more after
        for (int i = 0; i < 12; i++) {
            if (clinichours.validateHour(hours.get(11), "00", "PM", hours.get(i), "00", "PM")) { //AM in both before and after
                errorFound.add("error");
            }
        }

        //when hours is the same, min cant higher or equal before
        ArrayList<String> minutes = new ArrayList<String>();
        minutes.add("00");
        minutes.add("15");
        minutes.add("30");
        minutes.add("45");
        for (int i = 0; i < 4; i++) {
            if (clinichours.validateHour("1", minutes.get(3), "AM", "1", minutes.get(i), "AM")) { //AM in both before and after
                errorFound.add("error");
            }
        }

        //I just want to validate that errorFound.size() == 0
        assertEquals(0, errorFound.size());

    }

    @Test
    //This test will be used to make sure my validate name works for the Patient user accounts
    public void validateNamePatient() {
        String defaultName = " Omar ";
        //Create an ArrayList of a bunch of invalid name I don't want to allow!
        ArrayList<String> badName = new ArrayList<String>();
        //I want to create a bunch of names that have chars from [0,65) and (90,96) and (122,....)
        for (int i = 0; i < 65; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }
        for (int i = 91; i < 96; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }

        for (int i = 123; i < 127; i++) {
            //Convert integer to character
            char c = (char) i;
            //Add to the new name
            String newName = defaultName + c;
            badName.add(newName);
        }


        //Now I want to send them all through my validate name and make sure none of them come back true
        ArrayList<String> errorFound = new ArrayList<String>();

        for (int i = 0; i < badName.size(); i++) {
            String currName = badName.get(i);
            if (patientForm.isValidName(currName)) {
                errorFound.add(currName);
            }
        }

        //I just want to validate that errorFound.size() == 0
        assertEquals(0, errorFound.size());
    }


    //This test method will verify if my phone number validation is working for patient users
    @Test
    public void validatePhoneNumberPatient() {
        Random rand = new Random();
        //What makes a phone number valid??
        //It should only have number values (0,9)
        // IT SHOULD BE MAX 15 DIGITS!!
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Make an ArrayList with random invalid numbers
        ArrayList<String> invalidPhoneNumber = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            String randomNumber = "";
            // rand.nextInt((max - min) + 1) + min;
            //I want to make a random size bigger than 15
            int randomPhoneNumSize = rand.nextInt((30 - 15) + 1) + 15;
            int randomPhoneNumSize2 = rand.nextInt((9 - 0) + 1);
            if (randomPhoneNumSize % 2 == 0) {
                randomPhoneNumSize = randomPhoneNumSize2;
            }

            for (int j = 0; j <= randomPhoneNumSize; j++) {
                char letter = abc.charAt(rand.nextInt(abc.length()));
                randomNumber = randomNumber + j + "" + letter;
            }
            invalidPhoneNumber.add(randomNumber);
        }
        ArrayList<String> listOfErrors = new ArrayList<String>();
        for (int i = 0; i < invalidPhoneNumber.size(); i++) {
            //I want to check if these are acceptable numbers
            String currNumber = invalidPhoneNumber.get(i);
            if (patientForm.isValidPhoneNum(currNumber)) {
                listOfErrors.add(currNumber);

            }
        }

        //I should have listOfErrors.size() == 0
        assertEquals(0, listOfErrors.size());


        ArrayList<String> validPhoneNumber = new ArrayList<String>();

        //I'll now create of proper telephone numbers
        for (int i = 0; i < 100; i++) {
            String randomNumber = "";
            int randomPhoneNumSize = rand.nextInt((14 - 10) + 1) + 10;
            for (int j = 0; j <= randomPhoneNumSize; j++) {
                int randomNum = rand.nextInt((9 - 0) + 1);
                randomNumber = randomNumber + randomNum;
                //Add to array
            }
            validPhoneNumber.add(randomNumber);

        }
        //Now I want to make sure there was no errors
        for (int i = 0; i < validPhoneNumber.size(); i++) {
            String currNumber = validPhoneNumber.get(i);
            if (!patientForm.isValidPhoneNum(currNumber)) {
                listOfErrors.add(currNumber);
                System.out.println(currNumber);
            }

        }

        assertEquals(0, listOfErrors.size());
    }

    //This test method will verify if email validation is working for patient users
    @Test
    public void validateEmailPatient() {
        Random rand = new Random();

        String[] end = new String[]{".com",".ca"};
        String[] mail = new String[]{"gmail","yahoo","hotmail","uottawa"};
        String at = "@";

        ArrayList<String> badEmails = new ArrayList<String>();
        char[] charac = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'z', 'y','A','B','C','D','E',
                'F','G','H','I','J','K','L','M','N','O','P','Q'
                ,'R','S','T','U','V','W','X','Y','Z'};

        //random emails with no end
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=charac[rand.nextInt(charac.length)];
            email+=at;email+=mail[rand.nextInt(mail.length)];
            badEmails.add(email);
        }
        //random emails with no start
        for (int i = 0;i<3;i++){
            String email = "";
            email+=at;email+=mail[rand.nextInt(mail.length)];email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }
        //random emails with no @
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=mail[rand.nextInt(mail.length)];email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }
        //random emails with no mail
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=at;email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }

        ArrayList<String> errorFound = new ArrayList<String>();
        //for list of bad emails to see if valid or not
        for (int i = 0; i < badEmails.size(); i++) {
            String currEmail = badEmails.get(i);
            if (patientForm.isValidAddress(currEmail)){
                errorFound.add(currEmail);
            }
        }

        assertEquals(0, errorFound.size());
    }

    //This test method will verify if email validation is working for patient users
    @Test
    public void validateEmailEmployee() {
        Random rand = new Random();

        String[] end = new String[]{".com",".ca"};
        String[] mail = new String[]{"gmail","yahoo","hotmail","uottawa"};
        String at = "@";

        ArrayList<String> badEmails = new ArrayList<String>();
        char[] charac = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'z', 'y','A','B','C','D','E',
                'F','G','H','I','J','K','L','M','N','O','P','Q'
                ,'R','S','T','U','V','W','X','Y','Z'};

        //random emails with no end
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=charac[rand.nextInt(charac.length)];
            email+=at;email+=mail[rand.nextInt(mail.length)];
            badEmails.add(email);
        }
        //random emails with no start
        for (int i = 0;i<3;i++){
            String email = "";
            email+=at;email+=mail[rand.nextInt(mail.length)];email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }
        //random emails with no @
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=mail[rand.nextInt(mail.length)];email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }
        //random emails with no mail
        for (int i = 0;i<3;i++){
            String email = "";
            for (int n = 0;n<10;n++){
                email+=charac[rand.nextInt(charac.length)];
            }
            email+=at;email+=end[rand.nextInt(end.length)];
            badEmails.add(email);
        }

        ArrayList<String> errorFound = new ArrayList<String>();
        //for list of bad emails to see if valid or not
        for (int i = 0; i < badEmails.size(); i++) {
            String currEmail = badEmails.get(i);
            if (employeeForm.isValidAddress(currEmail)){
                errorFound.add(currEmail);
            }
        }

        assertEquals(0, errorFound.size());
    }


    //This test method will verify if address is valid with employee users
    @Test
    public void validateAddressEmployee() {
        Random rand = new Random();

        String[] end = new String[]{"way","lane","crescent","road"};

        ArrayList<String> badAddresses = new ArrayList<String>();
        char[] number= new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        char[] name=new char[]
                {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'z', 'y','A','B','C','D','E',
                'F','G','H','I','J','K','L','M','N','O','P','Q'
                ,'R','S','T','U','V','W','X','Y','Z'};

        //random addresses with no number but proper name
        String test = "";
        test+= "woodpark ";
        test+=end[rand.nextInt(end.length)];
        badAddresses.add(test);


        //random addresses with wrong name
        for (int i = 0;i<3;i++){
            String addy = "";
            addy+=number[rand.nextInt(number.length)];
            for (int n = 0;n<10;n++){
                addy+=name[rand.nextInt(name.length)];
            }
            addy+=" ";
            addy+=end[rand.nextInt(end.length)];
            badAddresses.add(addy);
        }

        ArrayList<String> errorFound = new ArrayList<String>();
        //for list of bad emails to see if valid or not
        for (int i = 0; i < badAddresses.size(); i++) {
            String curraddy = badAddresses.get(i);
            System.out.println(curraddy);
            if (employeeForm.isValidAddress(curraddy)){
                errorFound.add(curraddy);
            }
        }

        assertEquals(0, errorFound.size());
    }

    //This test method will verify if address in patient users
    @Test
    public void validateAddressPatient() {
        Random rand = new Random();

        String[] end = new String[]{"way","lane","crescent","road"};

        ArrayList<String> badAddresses = new ArrayList<String>();
        char[] number= new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        char[] name=new char[]
                {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                        'u', 'v', 'w', 'x', 'z', 'y','A','B','C','D','E',
                        'F','G','H','I','J','K','L','M','N','O','P','Q'
                        ,'R','S','T','U','V','W','X','Y','Z'};

        //random addresses with no number but proper name
        String test = "";
        test+= "woodpark ";
        test+=end[rand.nextInt(end.length)];
        badAddresses.add(test);


        //random addresses with wrong name
        for (int i = 0;i<3;i++){
            String addy = "";
            addy+=number[rand.nextInt(number.length)];
            for (int n = 0;n<10;n++){
                addy+=name[rand.nextInt(name.length)];
            }
            addy+=" ";
            addy+=end[rand.nextInt(end.length)];
            badAddresses.add(addy);
        }

        ArrayList<String> errorFound = new ArrayList<String>();
        //for list of bad emails to see if valid or not
        for (int i = 0; i < badAddresses.size(); i++) {
            String curraddy = badAddresses.get(i);
            System.out.println(curraddy);
            if (patientForm.isValidAddress(curraddy)){
                errorFound.add(curraddy);
            }
        }

        assertEquals(0, errorFound.size());
    }
}













