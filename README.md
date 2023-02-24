Console Java MVC application.

1.	Short project description 
The rent a car system allows clients to rent a car (with or without Driver). Regular clients get a discount based on frequency and money they have spend.
Seller finalize the order.
Administrator can manage everything, puts cars for rent, manage some of the user data and see statistics.
There is also service system.
The main user roles are:
• Anonymous User – can only see information about cars

• Registered User - can rent a car, add comment to a car, edit their personal data

and delete their account. They are also able to see history of their orders.
• Driver – drives the client to its destination and returns the car to the shop

• Seller – approve and finalize the order. After the car is returned
he gives it to the Site manager.

• Site manager – choose one of the workers to clean it.
Changes car’s cleaning status to start/finish

• Worker – Cleans the car, reports to the manager.

• Administrator – can manage (create, edit data and delete) cars,
change users role, approve renting and see statistics.

2.	Main Use Cases
-	Browse information - All user can see available cars and information about them.
-	Register - Anonymous User can register in the system 
- Change User Data - Users can view and edit their personal User Data
Administrator can view add new User, delete User and edit User Data of all Users (except for their username, password and email) and assign them Roles: User, Driver, Seller, Site manager, Admin
- Make order - User place an order and edit or cancel it two days before the order take place.
- Add/Edit Comment - User adds a comment about a car. 
Administrator may delete a comment if it’s aggressive or offensive
- Approve order - Seller finalize correct order. After the car is returned (by the Client or the Driver) he gives it to the service manager.
Administrator also can do this.
- Start/finish car cleaning - Site manager choose one of the workers to clean it. Change the status of the car. After the car is cleaned the status is finish and the car can be rented again
- Clean Car - The worker starts cleaning the car after finishing it the site manager changes the status of the car and returns it to the shop.
-	Add/Edit car - The administrator adds, edit and delete car

3.	Main Views
- All / Available cars - Presents information about all available cars.
Registered User can add a comment about a car.
-	User Data - Presents ability for registration, view and edit personal User Data
and history(User, Seller, Driver, Site manager)
-	Users - Presents ability to manage (CRUD) Users and their User Data to the Administrator
-	Rent a car - Presents ability for renting a car to registered User.
-	Pending orders - Presents ability to approve order to Seller and Administrator
-	Orders - Presents all current orders (with or without a Driver)
-	Clean - Presents all cars waiting for clean up
-	Car - Presents ability to manage (CRUD) Car only to the Administrator.
-	Statistics - Presents statistic to the Administrator - all rents and profit made for a period of time,
all users, seller, site manager and drivers history
