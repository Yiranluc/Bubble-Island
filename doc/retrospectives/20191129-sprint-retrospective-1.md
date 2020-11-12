# Sprint retrospective 1
SEM group 20


## Work overview

### Task 1
**Task:** Setting up the database on a remote host

**Assigned to:** Cheyenne

This task corresponds to the following **user stories:**
* "The game shall store usernames and hashed passwords in a database."
* "The game shall authenticate login attempts via a database."
* "The game shall store scores in a database, together with the name entered by the user."

**Estimated time effort:** 1 hour.

**Actual time effort:** 2 hours.

**Done:** Yes.

**Notes:** A mySQL database is now hosted on a TU Delft server.

### Task 2
**Task:**  Designing database schema

**Assigned to:** Cheyenne

This task corresponds to the following **user stories:**
* "The game shall store usernames and hashed passwords in a database."
* "The game shall authenticate login attempts via a database."
* "The game shall store scores in a database, together with the name entered by the user."

**Estimated time effort:** 1 hour.

**Actual time effort:** 1 hour.

**Done:** Yes.

**Notes:** Schemas "Users" and "Points" have been created.

### Task 3
**Task:** Build the libGDX environment  

**Assigned to:** Omar

This task corresponds to the following **user stories:** -

**Estimated time effort:** 1 hour.

**Actual time effort:** 1 hour.

**Done:** Yes

**Notes:** The libGDX library is used in developing this game.

### Task 4
**Task:** Start screen (GUI)

**Assigned to:** Valencio

This task corresponds to the following **user stories:**
* "The game shall show the user a start screen, which offers the options to create a new account or log into an already existing one."
* "The player shall not be able to start a new game without being logged into an account."

**Estimated time effort:** 3 hours.

**Actual time effort:** 3 hours.

**Done:** Yes.

**Notes:** The start screen contains all features needed for now.

### Task 5
**Task:** Rotate arrow

**Assigned to:** Omar

This task corresponds to the following **user stories:**
* "The player shall be able to rotate the arrow by moving their cursor in order to aim at the clustered bubbles."

**Estimated time effort:** 1 hour.

**Actual time effort:** 1 hour.

**Done:** No.

**Notes:** The feature is implemented, however not merged into the main branch yet.
Testing and code review need to be done.

### Task 6
**Task:** Signing up (back end)

**Assigned to:** Valencio & Cheyenne

This task corresponds to the following **user stories:**
* "The player shall be able to sign up with a unique username and password."
* "The game shall store usernames and encrypted passwords in a database."

**Estimated time effort:** 6 hours.

**Actual time effort:** Valencio: 10 hours, Cheyenne 5 hours.

**Done:** Yes.

**Notes:** This feature serves as exercise 3 of assignment 1.

### Task 7
**Task:** Sign-up screen (GUI)

**Assigned to:** Valencio & Cheyenne

This task corresponds to the following **user stories:**
* "The player shall be able to sign up with a unique username and password."

**Estimated time effort:** 2 hours.

**Actual time effort:** Valencio: 4 hours.

**Done:** Yes

**Notes:** This feature serves as exercise 3 of assignment 1.

### Task 8
**Task:** Game screen (GUI)

**Assigned to:** Ratish

This task corresponds to the following **user stories:**
* "The player shall be able to see an accurate timer while playing."

**Estimated time effort:** 6 hours.

**Actual time effort:** 7 hours.

**Done:** No.

**Notes:** A first version game screen was created. Code review is still needed.

### Task 9
**Task:** Game time visibility during game

**Assigned to:** Ratish

This task corresponds to the following **user stories:**
* "The player shall be able to see an accurate timer while playing."

**Estimated time effort:** 4 hours.

**Actual time effort:** 2 hours.

**Done:** No.

**Notes:** The feature has been developed, however code review is still needed.

### Task 10
**Task:** Shooting a ball.

**Assigned to:** Ratish

This task corresponds to the following **user stories:**
* "The player shall be able to shoot a bubble by clicking the left mouse button."

**Estimated time effort:** 5 hours.

**Actual time effort:** 6 hours.

**Done:** No.

**Notes:** The feature has been developed, however code review is still needed.

### Task 11
**Task:** Create a hexagon-shaped cluster with 20 bubbles.

**Assigned to:** Yiran

This task corresponds to the following **user stories:**
* "The game shall instantiate a hexagonal centerpiece of 20 bubbles of randomized colors (this is the bubble structure for the first level)."

**Estimated time effort:** 3 hours.

**Actual time effort:** 6 hours.

**Done:** No.

**Notes:** There is a hexagon shape with 20 bubbles in the cluster. 
However, applying 3 different colors on the bubbles is not finished due to unfamiliarity with libGDX and class dependencies. 

### Task 12
**Task:** Simple bubble class.

**Assigned to:** Ratish.

This task corresponds to the following **user stories:**
* "The game shall display a bubble or randomized color and an arrow at the bottom of the screen, once a new game is started."
* "The game shall instantiate a hexagonal centerpiece of 19 bubbles with randomized colors, once a new game is started (this is the bubble structure for the first level)."
* "The game shall provide at least 3 different colors of bubbles."

**Estimated time effort:** 3 hours.

**Actual time effort:** 4 hours.

**Done:** No

**Notes:** The feature has been developed, however code review is still needed.


### Task 13
**Task:** Logging in (back end) / authentication via database.

**Assigned to:** Valencio & Cheyenne

This task corresponds to the following **user stories:**
* "The player shall be able to log into their account, using a the password and username they choose."
* "The game shall authenticate login attempts via a database."
* "The player shall not be able to start a new game without being logged into an account."

**Estimated time effort:** 4 hours.

**Actual time effort:** Valencio: 3 hours.

**Done:** No.

**Notes:** This feature still needs to be tested.

### Task 14
**Task:** Login screen (GUI)

**Assigned to:** Valencio

This task corresponds to the following **user stories:**
*  "The player shall be able to log into their account, using a the password and username they choose."

**Estimated time effort:** 1 hour.

**Actual time effort:** 1 hour.

**Done:** Yes.

**Notes:** -

### Task 15
**Task:** Sprint retrospective 1.

**Assigned to:** Cheyenne 

**Time effort:** 4 hours.


## Main problems encountered

### Problem 1
**Description:** "At the start of the sprint, we did not explicitly discuss and clearly assign the tasks to each group member.
Until the middle of the first week, we gradually assign ourselves to the tasks, however, not during the face-to-face meeting. 
So we miss the time to work on the code from the beginning of this sprint and miss the opportunity to discuss who wants to do which parts exactly."

**Solution:** "Assigning tasks earlier."

### Problem 2
**Description:** "Learning about the libGDX library took more time than expected."

**Solution:** "Trying to code more, and spend less time reading documentations."

### Problem 3
**Description:** "The task division was not completely fair as some members did a lot more than others."

**Solution:** "Task division should be done at an early stage and everyone is expected to show participation."

### Problem 4
**Description:** "I (Cheyenne) spent way too much time on documentation.
This includes: making the requirement document, making gitlab issues, writing task descriptions, preparing agendas, making the sprint review and having to check other peoples work.
Due to this, I was not able to spend much time working on the actual code.
I also found that there was often poor communication."

**Solution:** "Better task division at the start of the sprint wil regards to documentation"

### Problem 5
**Description:** "The sprint backlog contained too many tasks."

**Solution:** "Make better time estimates and be realistic."

## Adjustments for the next sprint

Tasks need to be clear defined and given time estimates at the start of each sprint.
This will allow for better task division and set realistic goals.
The team needs better communication so we can resolve issues together.