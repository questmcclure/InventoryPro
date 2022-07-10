# Inventory Pro : Architect and Programming Portfolio

The Inventory Pro Android application was built to assist a user with managing a simple inventory. The app enables the user to create an item, read them from the database, update and delete them when necessary. The target users are homeowners, small business owners, collectors, e-commerce, etc.

Required screens included login and registration, home screen, item creation and update, and settings. The common theme behind the UI was simplicity allowing the user to interact with the application efficiently, with minimal clutter. I believe the design was relatively successful because it adhered to material design and only presented the user with just the right amount of information they would expect from each screen.

The coding process was rather simplistic, I approached it with the Observer pattern in mind. The idea was to leverage an SQLite database through event listeners and a dynamic recycle-view.

Testing was straightforward, I used the logcat class to read logic activity and the Android Studio emulator to test the user experience. This process is incredibly important, I don't believe an application could be built at all without testing it through console logs or an emulator. These strategies reveal common errors and logical inconsistencies that can very well wreck an entire application.

My biggest point of pain was during the initial full UI design where I laid out the application’s text views and other elements (without any code). I had trouble working with the XML GUI and design through Android Studio as it quickly become very complex to introduce a lot of these items. The innovation came with changing my approach to keeping the screen as simple as possible and trying to squeeze as much out of a view as I could. For example, using the hint feature to show the intent of a text field instead of writing what it is for in a text box above it.

I really like how the CRUD capabilities of the application turned out. They are designed in a way that feels natural to use, and the user can quickly alter their database efficiently. In my opinion, the app could definitely be an alternative to an excel based inventory as it is wieldy, easily accessed, and built for mobile.

# Screenshots

Login

![login](https://user-images.githubusercontent.com/49607645/178163640-e6f38bb2-f8bd-4444-80bf-4c3f1152ad29.png)

Register

![register](https://user-images.githubusercontent.com/49607645/178163642-ee6ad2e0-918e-4207-8035-5b4d2e83a9da.png)

Startup

![startup](https://user-images.githubusercontent.com/49607645/178163649-be00c4f2-5d40-4dea-86fc-b7f36359d74d.png)

Add Screen

![add_screen](https://user-images.githubusercontent.com/49607645/178163652-5d0a05fa-80ec-4e98-9591-54b66e93e78b.png)

Main Screen

![main_inventory](https://user-images.githubusercontent.com/49607645/178163655-7ad2679f-57de-4ef5-8c4f-4273dc07018f.png)

Update Screen

![update](https://user-images.githubusercontent.com/49607645/178163737-08e1f306-dd81-4bd5-8f51-556fe026d675.png)

Settings Screen

![settings](https://user-images.githubusercontent.com/49607645/178163732-39f6aca4-8c61-4936-8da3-f70352f97757.png)


