# Grade Calculator

A desktop application built with Kotlin and Jetpack Compose that helps calculate student grades and determine their academic standing.

## Features

- Input two test scores (0-100 range)
- Calculate total and average scores
- Automatic grade classification:
  - Cum Laude (100%)
  - Pass with Merit (80-99%)
  - Pass with Distinction (75-79%)
  - Pass (50-74%)
  - Fail (below 50%)
- Visual feedback with color-coded results:
  - Green: Scores above 74%
  - Blue: Passing scores (50-74%)
  - Red: Failing scores

## Technologies Used

- Kotlin
- Jetpack Compose for Desktop
- Material Design 3

## Requirements

- Java SDK 17
- Kotlin 2.1

## Getting Started

1. Clone the repository
2. Ensure you have Java SDK 17 installed
3. Build and run the project using Gradle:


## Usage

1. Enter the first test score (0-100) in the "Test Score 1" field
2. Enter the second test score (0-100) in the "Test Score 2" field
3. Click the "Calculate" button to see:
  - Total score
  - Average score
  - Final outcome with color-coded result

## Input Validation

- Scores must be between 0 and 100
- Invalid inputs will display an error message
- Empty or non-numeric inputs will be treated as 0

## Contributing

Feel free to submit issues and enhancement requests.
