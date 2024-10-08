This application is made with MVI architecture in mind. The reason for this is that it ensures unidirectional data flow and also separates the responsibility into layers which fits nicely into CLEAN as well as SOLID principles as MVI separates the responsibility into different layers which in this particular project separates into the following:

View: Responsible for holding the presentation logic as well as user interactions with each view elements.
ViewModel: Manages the state and minor business logic as well as processes the user intents and updating the view states accordingly.
Use Case: Use case holds the majority of the business as well as other logics that is required by each different feature and also serves as the intermediate with repository and data related operations before returning the resulted data to ViewModel for presentation on the view layer.
Repository: Serves as the single source of truth for data to their respective module in the application.

The application is a main function which calls courierApp that serves as a view delegator with a simple logic to determine which views should be shown based on the provided viewState value. Since this is a coding challenge and I prioritize speed, I have crammed both the order processing and offer management together in one. But under normal circumstances, I would prefer to separate out each module into their own individual delegator and viewModel for better maintainability as well as encapsulation.

The separation of different function such as OfferValidator, CostCalculator, TimeEstimation, PackageWeightMatcher is to facilitate better maintainability as well as modularity and purpose of PackageManager is to hold whatever features it requires to process package and since the way that the coding challenge is presented, the function is separated in a way that they don't require modification to existing code and instead is separated into each individual problem to solve.
