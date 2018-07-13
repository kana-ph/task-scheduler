# task-scheduler
A simple RESTful app that lets you input and schedule tasks.

## Scheduling Procedure
1. [Create a Project Plan](#create-project-plan)
2. [Add tasks to Project Plan](#create-task)
3. [Create Schedule](#create-schedule)

## REST Endpoints

### Project Plan
```js
// Example Project Plan Entity
{
  "id": 1,
  "name": "Plan Name"
}
```
#### Show all project plans
* URL: `GET /api/v1/plan`
* Response: List of [Project Plan Entities](#project-plan)

#### Show project plan
* URL: `GET /api/v1/plan/{id}`
  - `{id}` - A valid plan ID
* Response: A single [Project Plan Entity](#project-plan)

#### Create project plan
* URL: `POST /api/v1/plan`
* Body: A [Project Plan Entity](#project-plan) (_Without the `id`_)
* Response: The created [Project Plan Entity](#project-plan)

### Task
```js
// Example Task Entity
{
  "id": 3,
  "name": "Task Name",
  "dayDuration": 1,
  "dependencyIds": [1, 2]
}
```

#### Show all tasks
* URL: `GET /api/v1/plan/{planId}/task`
  - `{planId}` - The plan ID where the tasks will be fetched from.
* Response: List of [Task Entities](#task)

#### Show task
* URL: `GET /api/v1/plan/{planId}/task/{taskId}`
  - `{planId}` - The plan ID where the task will be fetched from.
  - `{taskId}` - A valid task ID that belongs to the given `planId`
* Response: A single [Task Entity](#task)

#### Create Task
* URL: `POST /api/v1/plan/{planId}`
  - `{planId}` - The plan ID where the task will belong to.
* Body: A [Task Entity](#task) (_Without the `id`_)
* Response: The created [Task Entity](#task)

### Schedule

#### Create Schedule
* URL: `POST /api/v1/schedule`
* Body:
  ```js
  {
    "planId": 1,
    "startDate": "2018-07-14"
  }
  ```
* Response:
  ```js
  {
    "plan": {
      "id": 1,
      "name": "Plan Name"
    },
    "startDate": "2018-07-14",
    "tasks": [
        {
          "task": {
            "id": 1,
            "name": "Sample Task #1",
            "dayDuration": 1
          },
          "startDate": "2018-07-14",
          "endDate": "2018-07-15"
        },
        {
          "task": {
            "id": 2,
            "name": "Sample Task #2",
            "dayDuration": 2
          },
          "startDate": "2018-07-15",
          "endDate": "2018-07-17"
        }
    ]
  }
  ```
