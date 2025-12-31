# 📝 Task Tracker CLI (Java)

**Project URL:** https://roadmap.sh/projects/task-tracker

A simple **command-line task tracking application** built in **Java**.  
This tool allows you to manage tasks by adding, updating, deleting, and tracking their progress using a JSON file for persistence.

The project is designed to practice:
- Command-line argument handling
- File system operations
- Basic JSON persistence (without external libraries)
- Object-oriented programming in Java

---

## Features

- Add new tasks
- Update existing tasks
- Delete tasks
- Mark tasks as **todo**, **in-progress**, or **done**
- List:
  - All tasks
  - Only todo tasks
  - Only in-progress tasks
  - Only completed tasks
- Persistent storage using a local `tasks.json` file
- No external libraries or frameworks required

---

## 📂 Project Structure

```
.
├── TaskTracker.java
├── tasks.json
└── utils
    ├── Task.java
    └── Utilities.java
```

---

## Task Properties

| Property    | Description |
|------------|-------------|
| `id`        | Unique task identifier |
| `description` | Short description of the task |
| `status`    | `todo`, `in-progress`, or `done` |
| `createdAt` | Date & time the task was created |
| `updatedAt` | Date & time the task was last updated |

---

## Requirements

- Java 17+ (recommended)
- No external libraries
- Runs entirely from the command line

---

## How to Compile and Run

### Compile
```bash
javac TaskTracker.java utils/*.java
```

### Run
```bash
java TaskTracker <command> [arguments]
```

---

## Commands & Usage

### Add a Task
```bash
java TaskTracker add "Buy groceries"
```

### Update a Task
```bash
java TaskTracker update <id> "New description"
```

### Delete a Task
```bash
java TaskTracker delete <id>
```

### Mark Task as In Progress
```bash
java TaskTracker mark-in-progress <id>
```

### Mark Task as Done
```bash
java TaskTracker mark-done <id>
```

### List Tasks

List all tasks:
```bash
java TaskTracker list
```

List todo tasks:
```bash
java TaskTracker list todo
```

List in-progress tasks:
```bash
java TaskTracker list in-progress
```

List completed tasks:
```bash
java TaskTracker list done
```

---

## Data Persistence

- Tasks are stored in a local file named `tasks.json`
- The file is automatically created if it does not exist
- Tasks are loaded on startup and saved on exit

---

## Known Limitations

- JSON parsing is done manually (no JSON libraries used)
- Task IDs are generated randomly
- Descriptions containing commas may affect parsing
- Minimal argument validation

---

## Possible Improvements

- Use a single task list instead of separate status lists
- Improve JSON parsing robustness
- Add argument validation and help commands
- Replace random IDs with sequential IDs
- Add unit tests

---

## 📄 License

This project is for educational purposes and is free to use and modify.