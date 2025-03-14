<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab System - Distance Calculate</title>
    
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
    
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100..900&family=Poppins:wght@300;700&display=swap" rel="stylesheet">

    <style>
        body {
		    font-family: 'Poppins', sans-serif;
		    text-align: center;
		    padding: 20px;
		    background-color: #e8f5e9; 
		    color: #1b5e20; 
		}
		.container {
		    max-width: 600px;
		    margin: auto;
		    padding: 20px;
		    border-radius: 10px;
		    box-shadow: 0 0 10px rgba(0, 100, 0, 0.2);
		    background-color: #ffffff; 
		}
		
		h2 {
		    color: #1b5e20; 
		}
		
		label {
		    font-weight: bold;
		    display: block;
		    margin-top: 10px;
		    color: #2e7d32; 
		}
		
		input {
		    width: 100%;
		    padding: 8px;
		    margin-top: 5px;
		    border: 1px solid #66bb6a;
		    border-radius: 5px;
		    background-color: #f1f8e9; 
		    color: #1b5e20;
		}
		
		#map {
		    height: 300px;
		    margin-top: 20px;
		    border-radius: 10px;
		    border: 2px solid #66bb6a; 
		}
		
		button {
		    margin-top: 15px;
		    padding: 10px 15px;
		    background-color: #2e7d32; 
		    color: white;
		    border: none;
		    border-radius: 5px;
		    cursor: pointer;
		}
		
		button:hover {
		    background-color: #1b5e20; 
		}
		
    </style>
</head>
<body>

<div class="container">
    <h2>Calculate Bill</h2>

    <form action="PaymentControllerServlet" method="post">
        <input type="hidden" name="action" value="calculate">

        <label for="pickupLocation">Pickup Location:</label>
        <input type="text" id="pickupLocation" name="pickupLocation" value="${location.pickupLocation}" readonly>

        <label for="destination">Destination:</label>
        <input type="text" id="destination" name="destination" value="${location.destination}" readonly>

        <button type="button" onclick="calculateDistance()">Calculate Distance</button>

        <label for="distance">Distance (km):</label>
        <input type="text" id="distance" name="distance" readonly>
        

        <div id="map"></div>

        <button type="submit">Proceed to Billing</button>
    </form>
</div>

<script>
    let map;
    let pickupMarker, destinationMarker;
    
    document.addEventListener("DOMContentLoaded", function(){
    	initMap();
    });
    
    function initMap() {
        map = L.map('map').setView([20.5937, 78.9629], 5);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; OpenStreetMap contributors'
        }).addTo(map);
    }

    function getCoordinates(address, type, callback) {
        fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${encodeURIComponent(address)}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    let lat = parseFloat(data[0].lat);
                    let lon = parseFloat(data[0].lon);

                    let color = type === 'pickup' ? 'red' : 'blue'; 

                    let marker = L.marker([lat, lon], {
                        icon: L.divIcon({
                            className: 'custom-icon',
                            html: `<div style="background-color:${color}; width:12px; height:12px; border-radius:50%;"></div>`,
                            iconSize: [12, 12]
                        })
                    }).addTo(map);

                    map.setView([lat, lon], 12);

                   
                    callback(lat, lon);
                } else {
                    alert(`Location not found: ${address}`);
                }
            })
            .catch(error => console.error('Error fetching location:', error));
    }

    function calculateDistance() {
        let pickupLocation = document.getElementById("pickupLocation").value;
        let destination = document.getElementById("destination").value;

        if (pickupLocation && destination) {
            getCoordinates(pickupLocation, 'pickup', function(pickupLat, pickupLon) {
                getCoordinates(destination, 'destination', function(destLat, destLon) {
                    if (pickupMarker) map.removeLayer(pickupMarker);
                    if (destinationMarker) map.removeLayer(destinationMarker);

                    pickupMarker = L.marker([pickupLat, pickupLon]).addTo(map).bindPopup("Pickup Location").openPopup();
                    destinationMarker = L.marker([destLat, destLon]).addTo(map).bindPopup("Destination").openPopup();

                    map.setView([pickupLat, pickupLon], 10);

                    let distance = getDistanceFromLatLonInKm(pickupLat, pickupLon, destLat, destLon);
                    document.getElementById("distance").value = distance.toFixed(2);
                });
            });
        } else {
            alert("Please enter both Pickup Location and Destination.");
        }
    }

    
    function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
        const R = 6371;
        const dLat = deg2rad(lat2 - lat1);
        const dLon = deg2rad(lon2 - lon1);
        const a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    
    function deg2rad(deg) {
        return deg * (Math.PI / 180);
    }

    window.onload = initMap;
    
    document.querySelector('form').addEventListener('submit', function(e) {
        const distanceInput = document.getElementById('distance');
        if(!distanceInput.value) {
            e.preventDefault();
            alert('Please calculate distance first!');
        }
    });
</script>
</body>
</html>
