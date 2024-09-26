# Lab 3: NY Times Bestselling Books

Course Link: [CodePath Android Course](https://courses.codepath.org/courses/and102/unit/3#!labs)

Submitted by: **Daniel Yankovich**

**NY Times Bestselling Books** is an app designed to display the current bestselling books based on NY Times data.

Time spent: **1** hours spent in total 

## Application Features

### Required Features

The following **required** functionality is completed:

- [x] (2 pts) **Live data is loaded from the NY Times API.**
    - ![Image/GIF showing additional email information](LoadData.gif)
- [x] (4 pts) **Books are displayed using a RecyclerView.**
    - Displays book ranking, cover, title, author, and description.
    - Book cover images are downloaded using Glide.
    - ![Image/GIF showing required features](Scroll.gif) 

### Stretch Features

The following **stretch** functionality is implemented:

- [x] (4 pts) **Improved layout and styling to match the NY Times website.**
    - Includes a "buy" button that links to Amazon.
    - ![Image/GIF showing stretch features](OpenAmazon.gif)

## Notes

Was a bit of a struggle getting the open to amazon link working but got it functional eventually.
## Resources

- [CodePath's AsyncHTTPClient library](https://guides.codepath.org/android/Using-CodePath-Async-Http-Client)
- [Displaying Images with Glide library](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library)
- [Parsing JSON responses to Models](https://guides.codepath.org/android/converting-json-to-models)
- [Parsing JSON with gson library](https://guides.codepath.org/android/Leveraging-the-Gson-Library#parsing-the-response)
- [Kotlin's Guide on Serialization](https://kotlinlang.org/docs/serialization.html)

## License

```plaintext
    Copyright [2024] [Daniel Yankovich]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.