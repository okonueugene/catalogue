# Catalogue Project

Catalogue Project is a Spring Boot application that serves as a classified ads platform. Users can post, edit, and search for ads; each ad may include multiple images that are stored on the file system in an ad-specific folder. The application leverages Spring Boot, Spring Data JPA, Thymeleaf, and Spring Security to deliver a robust, secure, and user-friendly experience.

## Features

- **User Authentication:**  
  Custom login and registration using Spring Security.

- **Ad Management:**  
  Post, edit, and delete ads with multiple image uploads. Each ad's images are stored in a dedicated folder named after the ad's ID.

- **Dynamic File Uploads:**  
  Upload multiple images per ad; file paths are stored in the database (ad_images table).

- **Related Ads:**  
  Display related ads based on matching category and subcategory.

- **Dynamic Dropdowns:**  
  Use AJAX/jQuery for dynamically loading subcategories and sublocations based on the selected category and location.

- **Responsive UI:**  
  Clean and responsive user interface built with Thymeleaf and Bootstrap.

## Technology Stack

- **Backend:**  
  Spring Boot, Spring MVC, Spring Security, Spring Data JPA

- **Database:**  
  (H2/MySQL/PostgreSQL – configurable via application.properties)

- **Frontend:**  
  Thymeleaf, HTML, CSS, JavaScript, jQuery, Bootstrap

- **File Storage:**  
  Local file system (files are stored in a folder structure like `/images/ad_images/{adId}/`)

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/catalogue-project.git
   cd catalogue-project
   ```

2. **Build and Run the Application:**
   ```bash
   ./gradlew bootRun
   ```      

3. **Access the Application:**
   Navigate to http://localhost:8080 in your web browser to access the catalogue project.

## Contributing

1. **Fork the Repository:**
   Fork this repository to your own GitHub account.

2. **Create a New Branch:**
   Create a new branch to work on your changes.

3. **Make Your Changes:**
   Make your changes and commit them to your new branch.

4. **Submit a Pull Request:**
   Submit a pull request to the original repository.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).


