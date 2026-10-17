const API = "http://localhost:8081";

// =============================
// PAGE LOAD
// =============================

window.onload = function () {


    loadStudents();
    loadSubjects();
    loadMarks();


};

// =============================
// DASHBOARD STATISTICS
// =============================

function updateDashboard() {


    fetch(API + "/students")
        .then(response => response.json())
        .then(students => {

            document.querySelector(
                ".dashboard .card:nth-child(1) p"
            ).textContent = students.length;

            document.querySelector(
                ".dashboard .card:nth-child(4) p"
            ).textContent = students.length;

        })
        .catch(error => {
            console.error("Student dashboard error:", error);
        });


    fetch(API + "/subjects")
        .then(response => response.json())
        .then(subjects => {

            document.querySelector(
                ".dashboard .card:nth-child(2) p"
            ).textContent = subjects.length;

        })
        .catch(error => {
            console.error("Subject dashboard error:", error);
        });


    fetch(API + "/marks")
        .then(response => response.json())
        .then(marks => {

            document.querySelector(
                ".dashboard .card:nth-child(3) p"
            ).textContent = marks.length;

        })
        .catch(error => {
            console.error("Marks dashboard error:", error);
        });


}

// =============================
// STUDENTS
// =============================

function loadStudents() {


    fetch(API + "/students")
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load students");
            }

            return response.json();

        })
        .then(students => {

            const list =
                document.getElementById("studentList");

            const gradeDropdown =
                document.getElementById("gradeStudentId");

            list.innerHTML = "";

            gradeDropdown.innerHTML =
                '<option value="">Select Student</option>';


            students.forEach(student => {

                const div =
                    document.createElement("div");

                div.className = "student";

                div.innerHTML = `

                <h3>${student.name}</h3>

                <p>
                    <strong>Roll Number:</strong>
                    ${student.rollNumber}
                </p>

                <p>
                    <strong>Email:</strong>
                    ${student.email}
                </p>

                <p>
                    <strong>Department:</strong>
                    ${student.department}
                </p>

                <p>
                    <strong>Year:</strong>
                    ${student.year}
                </p>

                <p>
                    <strong>Section:</strong>
                    ${student.section}
                </p>

                <button
                    type="button"
                    onclick="editStudent(${student.id})">
                    Edit
                </button>

                <button
                    type="button"
                    onclick="deleteStudent(${student.id})">
                    Delete
                </button>

            `;

                list.appendChild(div);


                const option =
                    document.createElement("option");

                option.value = student.id;

                option.textContent =
                    student.rollNumber +
                    " - " +
                    student.name;

                gradeDropdown.appendChild(option);

            });


            updateDashboard();

        })
        .catch(error => {

            console.error("Load students error:", error);

            document.getElementById(
                "studentList"
            ).innerHTML =
                "<p>Unable to load students.</p>";

        });


}

// =============================
// SAVE STUDENT
// =============================

function saveStudent(event) {


    event.preventDefault();

    const studentId =
        document.getElementById("studentId").value;


    const student = {

        rollNumber:
            document.getElementById("rollNumber")
                .value.trim(),

        name:
            document.getElementById("studentName")
                .value.trim(),

        email:
            document.getElementById("studentEmail")
                .value.trim(),

        department:
            document.getElementById("department")
                .value.trim(),

        year:
            Number(
                document.getElementById("year").value
            ),

        section:
            document.getElementById("section")
                .value.trim()

    };


    let url = API + "/students";

    let method = "POST";


    if (studentId) {

        url = API + "/students/" + studentId;

        method = "PUT";

    }


    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(student)

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to save student"
                );

            }

            return response.json();

        })
        .then(() => {

            alert(
                studentId
                    ? "Student updated successfully"
                    : "Student added successfully"
            );

            resetStudentForm();

            loadStudents();

        })
        .catch(error => {

            console.error("Save student error:", error);

            alert(error.message);

        });


}

// =============================
// EDIT STUDENT
// =============================

function editStudent(id) {


    console.log("Editing student ID:", id);


    fetch(API + "/students/" + id)
        .then(response => {

            if (!response.ok) {
                throw new Error(
                    "Unable to load student data"
                );
            }

            return response.json();

        })
        .then(student => {

            console.log("Student data received:", student);


            // Store student ID
            document.getElementById(
                "studentId"
            ).value = student.id;


            // Load previous Roll Number
            document.getElementById(
                "rollNumber"
            ).value = student.rollNumber || "";


            // Load previous Name
            document.getElementById(
                "studentName"
            ).value = student.name || "";


            // Load previous Email
            document.getElementById(
                "studentEmail"
            ).value = student.email || "";


            // Load previous Department
            document.getElementById(
                "department"
            ).value = student.department || "";


            // Load previous Year
            document.getElementById(
                "year"
            ).value = student.year || "";


            // Load previous Section
            document.getElementById(
                "section"
            ).value = student.section || "";


            // Change button to Update
            document.getElementById(
                "studentSubmitButton"
            ).textContent = "Update Student";


            // Show Cancel button
            document.getElementById(
                "studentCancelButton"
            ).style.display = "inline-block";


            // Scroll to student form
            document.getElementById(
                "studentForm"
            ).scrollIntoView({
                behavior: "smooth",
                block: "center"
            });


            // Focus on name field
            document.getElementById(
                "studentName"
            ).focus();

        })
        .catch(error => {

            console.error("Edit student error:", error);

            alert(
                "Unable to load student data: " +
                error.message
            );

        });


}

// =============================
// RESET STUDENT FORM
// =============================

function resetStudentForm() {


    document.getElementById(
        "studentForm"
    ).reset();


    document.getElementById(
        "studentId"
    ).value = "";


    document.getElementById(
        "studentSubmitButton"
    ).textContent = "Add Student";


    document.getElementById(
        "studentCancelButton"
    ).style.display = "none";


}

// =============================
// DELETE STUDENT
// =============================

function deleteStudent(id) {


    if (!confirm(
        "Are you sure you want to delete this student?"
    )) {
        return;
    }


    fetch(API + "/students/" + id, {

        method: "DELETE"

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to delete student"
                );

            }

        })
        .then(() => {

            alert("Student deleted successfully");

            loadStudents();

        })
        .catch(error => {

            console.error("Delete student error:", error);

            alert(error.message);

        });


}

// =============================
// SUBJECTS
// =============================

function loadSubjects() {


    fetch(API + "/subjects")
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load subjects");
            }

            return response.json();

        })
        .then(subjects => {

            const list =
                document.getElementById("subjectList");

            const subjectDropdown =
                document.getElementById("subjectId");

            list.innerHTML = "";

            subjectDropdown.innerHTML =
                '<option value="">Select Subject</option>';


            subjects.forEach(subject => {

                const div =
                    document.createElement("div");

                div.className = "student";

                div.innerHTML = `

                <h3>${subject.subjectName}</h3>

                <p>
                    <strong>Code:</strong>
                    ${subject.subjectCode}
                </p>

                <p>
                    <strong>Maximum Marks:</strong>
                    ${subject.maxMarks}
                </p>

                <button
                    type="button"
                    onclick="editSubject(${subject.id})">
                    Edit
                </button>

                <button
                    type="button"
                    onclick="deleteSubject(${subject.id})">
                    Delete
                </button>

            `;

                list.appendChild(div);


                const option =
                    document.createElement("option");

                option.value = subject.id;

                option.textContent =
                    subject.subjectCode +
                    " - " +
                    subject.subjectName;

                subjectDropdown.appendChild(option);

            });


            updateDashboard();

        })
        .catch(error => {

            console.error("Load subjects error:", error);

        });


}

// =============================
// SAVE SUBJECT
// =============================

function saveSubject(event) {


    event.preventDefault();


    const subjectId =
        document.getElementById("subjectIdHidden").value;


    const subject = {

        subjectCode:
            document.getElementById("subjectCode")
                .value.trim(),

        subjectName:
            document.getElementById("subjectName")
                .value.trim(),

        maxMarks:
            Number(
                document.getElementById("maxMarks").value
            )

    };


    let url = API + "/subjects";

    let method = "POST";


    if (subjectId) {

        url = API + "/subjects/" + subjectId;

        method = "PUT";

    }


    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(subject)

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to save subject"
                );

            }

            return response.json();

        })
        .then(() => {

            alert(
                subjectId
                    ? "Subject updated successfully"
                    : "Subject added successfully"
            );

            resetSubjectForm();

            loadSubjects();

        })
        .catch(error => {

            console.error("Save subject error:", error);

            alert(error.message);

        });


}

// =============================
// EDIT SUBJECT
// =============================

function editSubject(id) {


    fetch(API + "/subjects/" + id)
        .then(response => {

            if (!response.ok) {
                throw new Error("Unable to load subject");
            }

            return response.json();

        })
        .then(subject => {

            document.getElementById(
                "subjectIdHidden"
            ).value = subject.id;

            document.getElementById(
                "subjectCode"
            ).value = subject.subjectCode || "";

            document.getElementById(
                "subjectName"
            ).value = subject.subjectName || "";

            document.getElementById(
                "maxMarks"
            ).value = subject.maxMarks || "";


            document.getElementById(
                "subjectSubmitButton"
            ).textContent = "Update Subject";


            document.getElementById(
                "subjectCancelButton"
            ).style.display = "inline-block";


            document.getElementById(
                "subjectForm"
            ).scrollIntoView({
                behavior: "smooth",
                block: "center"
            });

        })
        .catch(error => {

            console.error("Edit subject error:", error);

            alert(error.message);

        });


}

// =============================
// RESET SUBJECT FORM
// =============================

function resetSubjectForm() {


    document.getElementById(
        "subjectForm"
    ).reset();


    document.getElementById(
        "subjectIdHidden"
    ).value = "";


    document.getElementById(
        "subjectSubmitButton"
    ).textContent = "Add Subject";


    document.getElementById(
        "subjectCancelButton"
    ).style.display = "none";


}

// =============================
// DELETE SUBJECT
// =============================

function deleteSubject(id) {


    if (!confirm(
        "Are you sure you want to delete this subject?"
    )) {
        return;
    }


    fetch(API + "/subjects/" + id, {

        method: "DELETE"

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to delete subject"
                );

            }

        })
        .then(() => {

            alert("Subject deleted successfully");

            loadSubjects();

        })
        .catch(error => {

            console.error("Delete subject error:", error);

            alert(error.message);

        });


}

// =============================
// MARKS
// =============================

function loadMarks() {


    fetch(API + "/marks")
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load marks");
            }

            return response.json();

        })
        .then(marks => {

            const list =
                document.getElementById("markList");

            list.innerHTML = "";


            Promise.all([

                fetch(API + "/students")
                    .then(response =>
                        response.json()
                    ),

                fetch(API + "/subjects")
                    .then(response =>
                        response.json()
                    )

            ])
                .then(([students, subjects]) => {

                    marks.forEach(mark => {

                        const student =
                            students.find(
                                s => s.id === mark.studentId
                            );

                        const subject =
                            subjects.find(
                                s => s.id === mark.subjectId
                            );


                        const div =
                            document.createElement("div");

                        div.className = "student";


                        div.innerHTML = `

                        <h3>
                            ${student
                                ? student.name
                                : "Unknown Student"}
                        </h3>

                        <p>
                            <strong>Subject:</strong>
                            ${subject
                                ? subject.subjectName
                                : "Unknown Subject"}
                        </p>

                        <p>
                            <strong>Marks:</strong>
                            ${mark.marks}
                            /
                            ${subject
                                ? subject.maxMarks
                                : "-"}
                        </p>

                        <button
                            type="button"
                            onclick="editMark(${mark.id})">
                            Edit
                        </button>

                        <button
                            type="button"
                            onclick="deleteMark(${mark.id})">
                            Delete
                        </button>

                    `;

                        list.appendChild(div);

                    });


                    updateDashboard();

                });

        })
        .catch(error => {

            console.error("Load marks error:", error);

        });


}

// =============================
// SAVE MARK
// =============================

function saveMark(event) {


    event.preventDefault();


    const markId =
        document.getElementById("markId").value;


    const studentId =
        Number(
            document.getElementById("markStudentId").value
        );


    const subjectId =
        Number(
            document.getElementById("subjectId").value
        );


    const marks =
        Number(
            document.getElementById("marks").value
        );


    const mark = {

        studentId: studentId,

        subjectId: subjectId,

        marks: marks

    };


    let url = API + "/marks";

    let method = "POST";


    if (markId) {

        url = API + "/marks/" + markId;

        method = "PUT";

    }


    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(mark)

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to save marks"
                );

            }

            return response.json();

        })
        .then(() => {

            alert(
                markId
                    ? "Marks updated successfully"
                    : "Marks added successfully"
            );

            resetMarkForm();

            loadMarks();

        })
        .catch(error => {

            console.error("Save marks error:", error);

            alert(error.message);

        });


}

// =============================
// EDIT MARK
// =============================

function editMark(id) {


    fetch(API + "/marks/" + id)
        .then(response => {

            if (!response.ok) {
                throw new Error("Unable to load marks");
            }

            return response.json();

        })
        .then(mark => {

            document.getElementById(
                "markId"
            ).value = mark.id;

            document.getElementById(
                "markStudentId"
            ).value = mark.studentId;

            document.getElementById(
                "subjectId"
            ).value = mark.subjectId;

            document.getElementById(
                "marks"
            ).value = mark.marks;


            document.getElementById(
                "markSubmitButton"
            ).textContent = "Update Marks";


            document.getElementById(
                "markCancelButton"
            ).style.display = "inline-block";


            document.getElementById(
                "markForm"
            ).scrollIntoView({
                behavior: "smooth",
                block: "center"
            });

        })
        .catch(error => {

            console.error("Edit marks error:", error);

            alert(error.message);

        });


}

// =============================
// RESET MARK FORM
// =============================

function resetMarkForm() {


    document.getElementById(
        "markForm"
    ).reset();


    document.getElementById(
        "markId"
    ).value = "";


    document.getElementById(
        "markSubmitButton"
    ).textContent = "Add Marks";


    document.getElementById(
        "markCancelButton"
    ).style.display = "none";


}

// =============================
// DELETE MARK
// =============================

function deleteMark(id) {


    if (!confirm(
        "Are you sure you want to delete these marks?"
    )) {
        return;
    }


    fetch(API + "/marks/" + id, {

        method: "DELETE"

    })
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to delete marks"
                );

            }

        })
        .then(() => {

            alert("Marks deleted successfully");

            loadMarks();

        })
        .catch(error => {

            console.error("Delete marks error:", error);

            alert(error.message);

        });


}

// =============================
// VIEW STUDENT GRADE
// =============================

function viewSelectedGrade() {


    const studentId =
        document.getElementById(
            "gradeStudentId"
        ).value;


    if (!studentId) {

        alert("Please select a student");

        return;
    }


    fetch(API + "/grades/" + studentId)
        .then(async response => {

            if (!response.ok) {

                const error =
                    await response.json();

                throw new Error(
                    error.detail ||
                    "Unable to get grade"
                );

            }

            return response.json();

        })
        .then(result => {

            const details =
                document.getElementById(
                    "gradeStudentDetails"
                );


            details.innerHTML = `

            <div class="grade-details">

                <h3>Student Result</h3>

                <p>
                    <strong>Name:</strong>
                    ${result.name}
                </p>

                <p>
                    <strong>Roll Number:</strong>
                    ${result.rollNumber}
                </p>

                <p>
                    <strong>Department:</strong>
                    ${result.department}
                </p>

                <p>
                    <strong>Year:</strong>
                    ${result.year}
                </p>

                <p>
                    <strong>Section:</strong>
                    ${result.section}
                </p>

                <p>
                    <strong>Percentage:</strong>
                    ${Number(result.average).toFixed(2)}%
                </p>

                <p>
                    <strong>Grade:</strong>
                    ${result.grade}
                </p>

            </div>

        `;

        })
        .catch(error => {

            console.error("Grade error:", error);

            alert(error.message);

        });


}
