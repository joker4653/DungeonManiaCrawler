# Usability Testing 
Generally, nice frontend - main issue is that the app needs to be restarted between games + some occurence of exceptions. 

# Modelling of Entities
Relationships with the Controller should probably be composition, not aggregation
Inheritance/implementation relationships should not have cardinalities

# Coupling and Cohesion
- Would maybe try and decouple some of the Game Creation/JSON logic from DungeonManiaController to better adhere to the Single Responsibility Principle
- The Factory Pattern is something you might want to look into to decouple object creation from business logic in M3
Nice work on keeping the Player relatively concise
- Some issues with the Law of Demeter in DungeonManiaController

# Design Patterns
- For the Strategy Pattern with Player the Player class doesn’t actually store a BattlingStrategy and from the current implementation multiple strategies can be used at the same time, so as a 'Strategy Pattern' it is incorrectly modelled 
- For enemies with the Strategy Pattern, you generally want all your strategies to be interchangeable with each other. - However, in this use case, a Spider will never use a Mercenary Attack Strategy so just something to be mindful of
    - It is also modelled incorrectly on the UML
- The FloorSwitch state pattern is a nice way to model the pressed/depressed states + Observer pattern between the player/enemy also makes sense

# Test Design - Structure 
Great testing plan

# Test Design - Quality 
Tests are generally understandable and well commented
No seed tests for randomised behaviour 

# Planning
- Really nice dependency diagram
- Ideally, tickets should be labelled with a priority

# Code Quality 
- Functions are generally well modularised + variable names are meaningful

# Ticket Lifecycle 
Great demo of the ticket lifecycle 
