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
2.1.	Browse information - All user can see available cars and information about them.
2.2.	Register - Anonymous User can register in the system 
2.3.	Change User Data - Users can view and edit their personal User Data
Administrator can view add new User, delete User and edit User Data of all Users (except for their username, password and email) and assign them Roles: User, Driver, Seller, Site manager, Admin
2.5.	Make order - User place an order and edit or cancel it two days before the order take place.
2.6.	Add/Edit Comment - User adds a comment about a car. 
Administrator may delete a comment if it’s aggressive or offensive
2.7.	Approve order - Seller finalize correct order. After the car is returned (by the Client or the Driver) he gives it to the service manager.
Administrator also can do this.
2.8.	Start/finish car cleaning - Site manager choose one of the workers to clean it. Change the status of the car. After the car is cleaned the status is finish and the car can be rented again
2.9.	Clean Car - The worker starts cleaning the car after finishing it the site manager changes the status of the car and returns it to the shop.
2.10.	Add/Edit car - The administrator adds, edit and delete car

3.	Main Views
3.1.	All / Available cars - Presents information about all available cars.
Registered User can add a comment about a car.
3.2.	User Data - Presents ability for registration, view and edit personal User Data
and history(User, Seller, Driver, Site manager)
3.3.	Users - Presents ability to manage (CRUD) Users and their User Data to the Administrator
3.4.	Rent a car - Presents ability for renting a car to registered User.
3.5.	Pending orders - Presents ability to approve order to Seller and Administrator
3.6.	Orders - Presents all current orders (with or without a Driver)
3.7.	Clean - Presents all cars waiting for clean up
3.8.	Car - Presents ability to manage (CRUD) Car only to the Administrator.
3.9.	Statistics - Presents statistic to the Administrator - all rents and profit made for a period of time,
all users, seller, site manager and drivers history
















