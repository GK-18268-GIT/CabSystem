<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mega City Cab System - Selected Cab List</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style type="text/css">
    body {
	  font-family: 'Montserrat', sans-serif;
	  background-color: #f0f8f0;
	  color: #2c5f2d;
	  margin: 0;
	  padding: 0;
	}
	
	.booking-container {
	  margin-top: 50px;
	  background-color: #ffffff;
	  border-radius: 10px;
	  box-shadow: 0 8px 20px rgba(0, 100, 0, 0.2);
	  padding: 25px;
	  max-width: 90%;
	  margin-left: auto;
	  margin-right: auto;
	}
	
	h2 {
	  color: #1e4620;
	  text-align: center;
	  margin-bottom: 40px;
	  font-weight: 600;
	  font-size: 2.5rem;
	}
	
	.table {
	  width: 100%;
	  border-radius: 8px;
	  margin-bottom: 30px;
	  background-color: #e8f5e9;
	}
	
	.table th {
	  background-color: #2c5f2d;
	  color: #ffffff;
	  text-align: center;
	  padding: 12px;
	  font-size: 1.1rem;
	}
	
	.table td {
	  text-align: center;
	  padding: 12px;
	  font-size: 1rem;
	  color: #2c5f2d;
	}
	
	.btn-primary, .btn-secondary, .btn-danger {
	  font-size: 14px;
	  padding: 8px 18px;
	  border-radius: 4px;
	  cursor: pointer;
	  transition: all 0.3s ease-in-out;
	  text-decoration: none;
	  display: inline-block;
	}
	
	.btn-primary {
	  background-color: #3aaf50; 
	      border: none;
	      color: white;
	      text-align: center;
	      width: 50%;
	      display: block;
	      margin: 20px auto;
	}
	
	.btn-primary:hover {
	  background-color: #2e7d32;
	  transform: translateY(-2px);
	}
	
	.btn-secondary {
	  background-color: #a5d6a7;
	  border: none;
	  width: 40%;
	  margin: 5px;
	  color: #2c5f2d;
	}
	    
	.btn-secondary:hover {
	   background-color: #81c784;
	   transform: translateY(-2px);
	}
	    
	 .btn-danger {
	    background-color: #d32f2f;
	    border: none;
	    width: 40%;
	    margin: 5px;
	    color: white;
	 }
	    
	 .btn-danger:hover {
	    background-color: #b71c1c;
	    transform: translateY(-2px);
	  }
	    
	.table td, .table th {
	  border: 1px solid #81c784;
	}
	
	.search-form {
	  display: flex;
	  justify-content: center;
	  margin-bottom: 20px;
	}
	
	.search-form input {
	  border-radius: 4px;
	  padding: 10px;
	  font-size: 1rem;
	  width: 60%;
	  background-color: #c8e6c9;
	  border: 1px solid #66bb6a;
	  color: #2c5f2d;
	}
	
	.search-form button {
	  padding: 10px 20px;
	  margin-left: 10px;
	  border-radius: 4px;
	  background-color: #3aaf50;
	  border: none;
	  color: #white;
	  font-size: 1rem;
	  cursor: pointer;
	}
	
	.search-form button:hover {
	  background-color: #2c8f40;
	  transform: translateY(-2px);
	}

  </style>
</head>

<body>
  <div class="booking-container">
    <h2>Selected Cab List</h2>
    
    <form action="BookingServlet?action=showSelectedCab" method="get" class="search-form">
      <input id="search-input" type="text" name="search" class="form-control" placeholder="Search by booking code..." value="${param.search}" onkeyup="selectedCabSearch()">
      <button type="submit" class="btn btn-secondary">Search</button>
    </form>
    
    <table id="cab-table" class="table table-bordered">
      <thead>
        <tr>
          <th>Booking ID </th>
          <th>Cab Code</th>
          <th>Cab Number</th>
          <th>Model </th>
          <th>Brand</th>
          <th>Capacity</th>
          <th>Base Price</th>
          <th>Cost Per Km</th>
          <th>Cab Image</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="cab" items="${selectedCabList}">
          <tr>
          	<td>${cab.bookingId}</td>
            <td>${cab.cabCode}</td>
            <td>${cab.cabNumber}</td>
            <td>${cab.model}</td>
            <td>${cab.brand}</td>
            <td>${cab.capacity}</td>
            <td>${cab.basePrice}</td>
            <td>${cab.costPerKm}</td>
            <td>
                <img src="${pageContext.request.contextPath}/uploads/${cab.cabImage}" alt="${cab.model}" style="max-width: 100px;">
            </td>
            <td>
              <a href="BookingServlet?action=update&bookingId=${cab.bookingId}" class="btn btn-secondary">Update</a>
              <a href="BookingServlet?action=delete&bookingId=${cab.bookingId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this cab?')">Delete</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    
  </div>
  
  <script type="text/javascript">
    function selectedCabSearch() {
    var input = document.getElementById("search-input").value.toLowerCase();
    var table = document.getElementById("cab-table");
    var rows = table.getElementsByTagName("tr");
    
    for (var i = 1; i < rows.length; i++) { 
        var cells = rows[i].getElementsByTagName("td");
        var bookingId = cells[0].textContent.toLowerCase(); 
        var cabCode = cells[1].textContent.toLowerCase();   
        
        if (bookingId.includes(input) || cabCode.includes(input)) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}
</script>
  
</body>
</html>
