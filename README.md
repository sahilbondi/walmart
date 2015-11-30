				Read Me

Test Problem:

1. Automate an end-to-end user e-commerce transaction flow using any open source tool for www.walmart.com with an existing customer on Chrome or Safari browser.

Scenario to automate:
1. Login using existing account
2. Perform a search on home page from a pool of key words given below
3. Identify an item from the result set that you can add to cart
4. Add the item to cart
5. Validate that item added is present in the cart and is the only item in the cart

Test Data:
• Account details: create your own account
• Search terms: tv, socks, dvd, toys, iPhone

Solution:

I decided to break the problem into multiple steps and making sure that the current step is correct and functioning properly. Following is the flow diagram of my solution.
//Attached in the email
 
I used the following function to implement my design to automate the process.

Public static void homepage(WebDriver driver): In this method my code goes to the website www.walmart.com and then checks the welcome message to see if this is the correct page.
Needs to improve: I believe just checking the right welcome message might not be the most secure way since anyone can have the same welcome message.

public static void extraAdvertisements(WebDriver driver) : Sometimes there were pop-ups on the home page(such as sign up to get offers through email) and my previous code wasn’t handling this case. So in this method my code checks if there is any pop-up and closes it. 
Needs to improve: My code only handles one handles only one pop-up and for this page only. I assuming there will not be any more pop-up once user logs in. There can be more checks for multiple pop-ups on every page.

public static void signInProcess(WebDriver driver): In this method my code clicks the sign-in button on the main page and then takes the user to next page where he/she can enter the  login credentials.  My code prints out if the login was successful or unsuccessful.  I check for the “incorrect username/password” message to judge if the login was unsuccessful but I haven’t found a concrete way to check if the login was successful. Once the sign-in button is clicked program goes to the next page.
Needs to improve: I am assuming that the previous page is seure because in this method login credentials are provided. I need to find a better way to secure this login process.

public static void search(WebDriver driver): In this method my code goes to the search bar and enters “tv” and clicks the search button. I have made an assumption the I need to automate for only one of the 5 items given in the problem statement.

public static void addFirstMatchedItemToCart(WebDriver driver): Once the search results are displayed I store them in an Array and go through each one of them. In each item I check for the key words(LED and HDTV) in its title. If those key words are present then my code clicks the image of that result which then takes the program to next page where it can add the item in the cart. Then I store the “Item Id” so that I can match it on the next page. If none of the item matches the description then I print an error statement on the console and stop the program by throwing a “return” statement. It can be done in a better way by throwing exceptions as well.
Needs to improve: The way I am validating the item in the result set is very weak because there are many items that can have those two keywords(LED and HDTV) such as a tv cleaner or a tv stand..etc. So I need to find a better way to authenticate the item before adding it to the cart.

public static void viewCart(WebDriver driver): This function basically clicks the view cart button and takes the program to the checkout page.

public static void checkingCart(WebDriver driver): This functions checks if the cart only has one item or not. If it passes the first check then it gets the item-id and compares it to the previously stored item-id(before adding it to cart). 
