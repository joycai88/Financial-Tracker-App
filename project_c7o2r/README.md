# Personal Project - Student Expenses Tracker

My personal project is a student expenses tracker that allows the user to create an account with a monthly budget
and track both their **daily expenses and savings**. Going to university is a big change,
as students now have the freedom to spend money however they want. As such, being able to budget expenses is an essential 
skill to ensuring that there will be enough savings to pay for school fees, rent, groceries, etc. My application aims to help
make this process easier my allowing them to create **multiple accounts**, each with a budget dedicated to a specific area
(ex. entertainment, food, gifts, etc.). Furthermore, the application will also be able to compile data to graph visual representations
of the users spending habits. Hence, this desktop application will be mainly catered towards students, but any user who finds that
the features provided by the application suits their needs can use it. 

## Motivation
This project interests me because I have always been trying to look for a good expenses tracker,
but none of them seemed to have all the features I wanted, and I was only able to track
everything to one budget/account instead of opening multiple. In addition, many of the 
spending statistics were locked behind a paywall. Thus, this project would be able to solve
a personal problem and can also be useful to all students.

## User Stories
- As a user, I want to be able to make a new account and add a monthly budget
- As a user, I want to be able to add an expense to a specific budget
- As a user, I want to be able to view how much money is left in a monthly budget
- As a user, I want to receive a warning when I am spending over the monthly budget
- As a user, I want to have the option to save my account in its current state  
- As a user, I want to have the option to load my expense tracker account from file

## Instructions for Grader
- You can generate the first required action related to adding Xs to a Y by clicking the
  "New Budget" button in the account menu to add a new budget to account.  
- You can generate the second required action related to adding Xs to a Y by selecting a specific budget
  within account and removing it by clicking the "remove" button. 
- You can locate my visual component on the starting menu (background that looks like notebook)
- You can save the state of my application by clicking the "Save" button that can be found on all pages.
- You can reload the state of my application by clicking the "Load from File" button from the start menu when
  the application launches.

## Phase 4: Task 2
>Tue Apr 11 10:29:43 PDT 2023 
> 
>School created with a monthly amount of $200.0
> 
>Tue Apr 11 10:29:53 PDT 2023
> 
>Food created with a monthly amount of $100.0
> 
>Tue Apr 11 10:29:59 PDT 2023
> 
>Rent created with a monthly amount of $2000.0
> 
>Tue Apr 11 10:30:22 PDT 2023
> 
>Bread added to Food
> 
>Tue Apr 11 10:30:37 PDT 2023
> 
>Error created with a monthly amount of $20.0
> 
>Tue Apr 11 10:30:40 PDT 2023
> 
>Error was removed from account
> 
>Tue Apr 11 10:30:57 PDT 2023
> 
>Textbooks added to School
>

## Phase 4: Task 3
Looking at the UML diagram and my code, I would first want to refactor my ActionListeners in
the StartScreenGUI class. The majority of my action listeners are implemented in the same <code>actionPerformed()</code>
method using if/else cases. This reduces efficiency as the program needs to cycle through all of them until the correct
command is found. Since I already implemented a separate ActionListener for the starting screen, similar steps could be 
taken to extract budget and expense related actions into their own ActionListener classes. Performing this refactoring 
will also make the code easier to maintain if I decide to expand my program and add more functionality, creating an 
organized way of tracking different actions instead of having everything in one class.

Another way I can refactor my code is making both the <code>TrackerAppGUI</code> and <code>StartScreenGUI</code> extend 
the <code>Functionality</code> class. Since both GUI classes contain very similar methods for creating JPanels and 
JFrames, there are a few cases of duplication in my code. By extracting similar methods into the functionality class, 
all subclasses will be able to access them. By reducing duplication, I can reduce complexities in my code that may
be beneficial as my code expands. For example, if more GUI classes are created in the future,
which will allow them to access commonly used methods without needed to write duplicate code.