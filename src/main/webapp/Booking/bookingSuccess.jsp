<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booking Confirmed</title>
  <style>
    :root {
      --skyBlue: #8ecae6;
      --blueGreen: #219ebc;
      --prussianBlue: #023047;
      --yellow: #ffb703;
      --orange: #fb8500
    }

    body {
      font-family: 'Segoe UI', system-ui, sans-serif;
      background: var(--prussianBlue);
      margin: 0;
      padding: 2rem;
      display: flex;
      justify-content: center;
      min-height: 80vh;
    }

    .confirmation-card {
      background: var(--skyBlue);
      border-radius: 12px;
      box-shadow: 0 4px 16px rgba(0,0,0,0.08);
      max-width: 800px;
      width: 100%;
      padding: 2rem;
    }

    h1 {
      color: var(--orange);;
      margin-top: 0;
      font-size: 1.8rem;
    }

    .confirmed-badge {
      background: #10b981;
      color: white;
      padding: 0.5rem 1rem;
      border-radius: 50px;
      display: inline-block;
      font-weight: 600;
      margin-bottom: 1.5rem;
    }

    .content-section {
      display: flex;
      gap: 2rem;
      margin: 2rem 0;
    }

    .car-image {
      width: 50%;
      border-radius: 8px;
      object-fit: cover;
    }

    .details-section {
      width: 50%;
    }

    .detail-card {
      background: #7eb0c2;
      padding: 1.25rem;
      border-radius: 8px;
      margin-bottom: 1rem;
    }

    .detail-row {
      display: flex;
      margin-bottom: 0.75rem;
    }

    .detail-label {
      font-weight: 600;
      width: 80px;
      color: #64748b;
    }

    .detail-value {
      flex: 1;
    }

    .home-btn {
      background: var(--prussianBlue);
      color: white;
      border: none;
      padding: 0.75rem 1.5rem;
      border-radius: 8px;
      font-weight: 600;
      cursor: pointer;
      margin-top: 1.5rem;
      transition: background 0.2s;
      width: 100%;
      max-width: 200px;
    }

    .home-btn:hover {
      background: #1d4ed8;
    }


  </style>
</head>
<body>
<div class="confirmation-card">
  <div class="confirmed-badge">Booking Confirmed</div>
  <h1>Thank you, ${userName}!</h1>


  <div class="content-section">
    <img src="${carPic}" alt="${carName}" class="car-image">

    <div class="details-section">
      <div class="detail-card">
        <div class="detail-row">
          <span class="detail-label">Car:</span>
          <span class="detail-value">${carName}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">From:</span>
          <span class="detail-value">${startDate}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">To:</span>
          <span class="detail-value">${endDate}</span>
        </div>

        <div class="detail-row">
          <span class="detail-label">Details:</span>
          <span class="detail-value">${carDetails}</span>
        </div>

      </div>

    </div>
  </div>

  <button onclick="" class="home-btn">Return to Home</button>
</div>
</body>
</html>