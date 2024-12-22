document.getElementById("patientForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const age = document.getElementById("age").value;
    const disease = document.getElementById("disease").value;

    const patientData = { name, age, disease };

    fetch("/api/patients", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(patientData),
    })
        .then((response) => {
            if (response.ok) {
                alert("Patient data submitted successfully!");
                // Optionally reload the page to show the newly added patient
                window.location.reload();
            } else {
                alert("Failed to submit patient data.");
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("Error submitting data.");
        });
});