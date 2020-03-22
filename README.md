# NewsApiFeedADR
Before implementing the challenge the task had been analysed and a pool of tasks was written.    
  
## Backlog  
### Integration  
ABR-101-Integrate MVP and DI  
ABR-102-Integrate Retrofit  
ABR-103-Integrate RxJava  
ABR-104-Integrate Realm  
ABR-105-Integrate News API  
### Main story  
ABR-201-Request the data from the API  
ABR-202-Put the data into the DB  
ABR-203-Show the data on the list with pagination  
ABR-204-Make the UI according to the mockups  
ABR-205-Handle the configuration change (persist visible element)    
ABR-206-Handle the click and open a browser  
ABR-207-Introduce the offline mode  
### Additional story  
ABR-301-Integrate Pull-to-refresh  
ABR-302-Make the toolbar hide    
ABR-303-Change items when locale is changed    
### Testing  
ABR-401-Prepare Unit tests  
  
## Developer notes  
  
The app is build using MVP pattern with Dagger2 as a DI engine with RxJava, retrofit and RealmDB.
The app supports online and offline modes.  
The app handles screen rotation and adjusts the amount of cells to show per row. It also handles the 
change of a Locale, so if it's changed the news of that Locale will be retrieved (US locale is used by default).  
While rotating the first visible news card remains unchanged.  
To support offline mode correctly each successful network request gets associated with a timestamp of
when it was received. And also a "weight" of each request is calculated. Weight is a number indicating 
how many default pages this or that request contains. 21 item equals to the weight of 3, 7 items – to the weight of 1.       
To store the data there are 2 tables:   
- NewsTimestamp model is for having a list of timestamps with associated weights.  
- News model is for storing the data of each article.  
Pagination library (a part of Android architecture components) is used for supporting the pagination. 
To make it work we heave NewsDataSource defined that manages getting the data and pasting it to the UI.  
NewsProviderImpl handles the process of providing the data. It checks the DB according to the amount of 
items to request and a current page to get, it there is data in the DB – it returns it, otherwise a call
to the API is made.  
If a new data is needed to get – pull to refresh component is needed to be triggered.   
  
## Things to improve  
Increase unit tests coverage  
Improve UX by indicating if a user is online or offline now    
Improve UI by adjusting the styles  
Improve UX by opening an article inside the app  
  
## Installation guide  
In order to retrieve the data you will need a valid API key from the newsapi.org.  
Please paste it into security.gradle file. The result should look like this:  
newsOrgApiKey  : "\"1234567890abcdef1234567890abcdef\""