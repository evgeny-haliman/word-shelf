function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

function clearTBody() {
    let tbody = document.getElementsByTagName("tbody")[0];

    if (tbody) {
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }
    }
}

function getRowIndex() {
    const selectedElement = document.querySelector('.is-selected');
    if (selectedElement) {
        return document.querySelector('.is-selected').rowIndex;
    } else return -1;

}

function deleteSelectedClass(number) {
    if (getRowIndex() !== -1) {
        document.querySelectorAll('tr')[number].classList.remove('is-selected')
    }
}

function selectRow() {
    return function (event) {
        if (event.target.tagName === "TD" || event.target.tagName === "TH") {
            let closestTr = event.target.closest("tr");

            if (closestTr) {
                deleteSelectedClass(getRowIndex());
                closestTr.classList.add('is-selected')
            }
        }
    }

}

function moveSelectedRow() {
    return function (event) {
        let number = getRowIndex();
        if (event.key === "ArrowUp" && number >= 2) {
            deleteSelectedClass(number);
            document.querySelectorAll('tr')[number - 1].classList.add('is-selected');
        } else if (event.key === "ArrowDown" && number < document.querySelectorAll('tr').length - 1) {
            deleteSelectedClass(number);
            document.querySelectorAll('tr')[number + 1].classList.add('is-selected');
        }
    };
}

function closeModal() {
    document.querySelector('.modal-close').click();
}

function closeUpdate() {
    document.getElementById('closeUpdateId').click();
}

function closeModalUp() {
    document.getElementById('modal-close-up').click();
}

function closeModalIn() {
    document.getElementById('modal-close').click();
}

function isNickValid(text) {
    return /^(?=.{3,15}$)[a-zA-Z0-9_-]+$/.test(text);
}

function nickNameIn() {
    const nickInput = document.getElementById("nickInputIn");
    const validationMessage = document.getElementById("nickMessageIn");

    const text = nickInput.value.trim();

    if (isNickValid(text)) {
        validationMessage.textContent = "";
    } else {
        validationMessage.textContent = "Name should be between 3 and 15 characters";
    }
}

function nickName() {
    const nickInput = document.getElementById("nickInput");
    const validationMessage = document.getElementById("nickMessage");

    const text = nickInput.value.trim();

    if (isNickValid(text)) {
        validationMessage.textContent = "";
    } else {
        validationMessage.textContent = "Name should be between 3 and 15 characters";
    }
}

function isPassValid(text) {
    return /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*^?&]{11,}$/.test(text);
}

function passIn() {
    const passInput = document.getElementById("passInputIn");
    const validationMessage = document.getElementById("passMessageIn");

    const text = passInput.value.trim();

    if (isPassValid(text)) {
        validationMessage.textContent = "";
    } else {
        validationMessage.textContent = "One uppercase letter, one digit, one special character from the set @$!%*^?&, min 12";
    }
}

function pass() {
    const passInput = document.getElementById("passInput");
    const validationMessage = document.getElementById("passMessage");

    const text = passInput.value.trim();

    if (isPassValid(text)) {
        validationMessage.textContent = "";
    } else {
        validationMessage.textContent = "One uppercase letter, one digit, one special character from the set @$!%*?&, min 12";
    }
}

function fillTable() {
    const tbody = document.getElementsByTagName('tbody')[0]; // Replace 'your-tbody-id' with the actual ID of your <tbody>.
    if (books.length > 0) {
        books.forEach((book, index) => {
            const tr = document.createElement('tr');
            if (index === 0) {
                tr.classList.add('is-selected');
            }
            tr.innerHTML = `
    <th>${index + 1}</th>
    <td>${book.word}</td>
    <td>${book.transcription}</td>
    <td>${book.translation}</td>
    <td>${book.sentence}</td>
  `;
            tbody.appendChild(tr);
        });
    }
}

function showName() {
    document.getElementById("title_4").textContent = `Hello ${name}!`;
}

function submitSignIn() {
    const passInput = document.getElementById("passInputIn").value.trim();
    const nickInput = document.getElementById("nickInputIn").value.trim();

    if (isNickValid(nickInput) && isPassValid(passInput)) {
        name = nickInput;
        const data = {
            name: nickInput,
            password: passInput
        };
        fetch('https://localhost:49161/auth/signing', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    response.json()
                        .then(resp => {
                            jwt = resp.jwt;
                            books = resp.books
                            closeModalIn();
                            showName();
                            clearTBody();
                            document.getElementById("addId").classList.remove("hidden");
                            document.getElementById("deleteId").classList.remove("hidden");
                            document.getElementById("updateId").classList.remove("hidden");
                            document.getElementById("saveId").classList.remove("hidden");
                            document.getElementById("logoutId").classList.remove("hidden");
                            document.getElementById("deleteUserId").classList.remove("hidden");
                            document.getElementById("signupId").classList.add("hidden");
                            document.getElementById("loginId").classList.add("hidden");
                            fillTable();
                        })
                } else {
                    document.getElementById("nickMessageIn").textContent = "Credentials are wrong"
                }
            })
    }
}

function submitSignUp() {
    const passInput = document.getElementById("passInput").value.trim();
    const nickInput = document.getElementById("nickInput").value.trim();

    if (isNickValid(nickInput) && isPassValid(passInput)) {
        name = nickInput;
        const data = {
            name: nickInput,
            password: passInput,
            confirmPassword: passInput,
        };
        fetch('https://localhost:49161/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    response.json()
                        .then(resp => {
                            jwt = resp.jwt;
                            closeModalUp();
                            showName();
                            clearTBody();
                            document.getElementById("addId").classList.remove("hidden");
                            document.getElementById("deleteId").classList.remove("hidden");
                            document.getElementById("updateId").classList.remove("hidden");
                            document.getElementById("saveId").classList.remove("hidden");
                            document.getElementById("logoutId").classList.remove("hidden");
                            document.getElementById("deleteUserId").classList.remove("hidden");
                            document.getElementById("signupId").classList.add("hidden");
                            document.getElementById("loginId").classList.add("hidden");
                            document.getElementsByTagName("tr")[0].classList.add("is-selected");
                        })
                } else {
                    document.getElementById("nickMessage").textContent = "This nickname is already exists"
                }
            })
    }
}

function saveData() {
    const table = document.querySelector('table');
    const rows = table.querySelectorAll('tbody tr');
    const jsonData = [];

    rows.forEach((row) => {
        const cells = row.querySelectorAll('td');
        const word = cells[0].textContent.trim();
        const transcription = cells[1].textContent.trim();
        const translation = cells[2].textContent.trim();
        const sentence = cells[3].textContent.trim();

        const dataObject = {
            word: word,
            transcription: transcription,
            translation: translation,
            sentence: sentence
        };
        jsonData.push(dataObject);
    });

    const finalJson = {
        json: jsonData
    };

    const jsonString = JSON.stringify(finalJson);
        fetch('https://localhost:49161/update', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${jwt}`,
                'Content-Type': 'application/json'
            },
            body: jsonString
        })
            .then(response => {
                if (!response.ok) {
                    alert("Your data was not saved");
                } else {
                    setSaveDisabled();
                }
            });
}

function setSaveChanged() {
    let textElement = document.getElementById('saveId');

    if (textElement.classList.contains('is-primary')) {
        textElement.classList.remove('is-primary');
        textElement.classList.add('is-text');
        textElement.classList.remove('disabled');
    }
}

function setSaveDisabled() {
    let textElement = document.getElementById('saveId');

    if (textElement.classList.contains('is-text')) {
        textElement.classList.remove('is-text');
        textElement.classList.add('is-primary');
        textElement.classList.add('disabled');
    }
}

function submitAdd() {
    const wordInput = document.getElementById("wordInput").value.trim();
    const transcriptionInput = document.getElementById("transcriptionInput").value.trim();
    const translationInput = document.getElementById("translationInput").value.trim();
    const sentenceInput = document.getElementById("sentenceInput").value.trim();
    if (wordInput !== "" && transcriptionInput !== "" && translationInput !== "" && sentenceInput !== "") {
        const index = document.getElementsByTagName('th').length - 4;

        const tbody = document.getElementsByTagName('tbody')[0];
        const tr = document.createElement('tr');
        deleteSelectedClass(getRowIndex());
        tr.classList.add('is-selected');
        tr.innerHTML = `
    <th>${index}</th>
    <td>${wordInput}</td>
    <td>${transcriptionInput}</td>
    <td>${translationInput}</td>
    <td>${sentenceInput}</td>
  `;
        tbody.appendChild(tr);
        setSaveChanged();
        closeModal();
    } else {
        const warnMessage = document.getElementById('wordMessage');
        warnMessage.textContent = "Each field should not be empty"
    }
}

function deleteRowByThValue(thValue) {
    if (getRowIndex() > 0) {
        let tbody = document.getElementsByTagName("tbody")[0];
        let rows = tbody.getElementsByTagName("tr");

        // Find and delete the row with the specified <th> value
        for (let i = 0; i < rows.length; i++) {
            let row = rows[i];
            let cell = row.getElementsByTagName("th")[0];
            if (Number(cell.textContent) === Number(thValue)) {
                tbody.removeChild(row);
                break;
            }
        }

        // Update row numbering for the remaining rows
        for (let i = 0; i < rows.length; i++) {
            rows[i].getElementsByTagName("th")[0].textContent = (i + 1).toString();
        }
        setSaveChanged();
    } else showNotification();
}

function getValuesByThValue(thValue) {
    let tbody = document.querySelector('tbody');
    let rows = tbody.querySelectorAll('tr');
    let words = [];

    for (let i = 0; i < rows.length; i++) {
        let row = rows[i];
        let th = row.querySelector('th');

        if (Number(th.textContent) === thValue) {
            let tds = row.querySelectorAll('td');

            for (let j = 0; j < tds.length; j++) {
                words.push(tds[j].textContent);
            }
             // Exit the loop once we've found the matching <th>
        }
    }
    return words;
}

function fillForm() {
    if (!getRowIndex() > 0) {
        showNotification();
    } else {
        const words = getValuesByThValue(getRowIndex());
        let inputWord = document.getElementById("wordInputId");
        let inputTransc = document.getElementById("transcriptionInputId");
        let inputTrans = document.getElementById("translationInputId");
        let inputSentence = document.getElementById("sentenceInputId");

        inputWord.value = words[0];
        inputTransc.value = words[1];
        inputTrans.value = words[2];
        inputSentence.value = words[3];
    }
}

function submitUpdate() {
    const wordInput = document.getElementById("wordInputId").value.trim();
    const transcriptionInput = document.getElementById("transcriptionInputId").value.trim();
    const translationInput = document.getElementById("translationInputId").value.trim();
    const sentenceInput = document.getElementById("sentenceInputId").value.trim();
    const arr = [wordInput, transcriptionInput, translationInput, sentenceInput];
    if (wordInput !== "" && transcriptionInput !== "" && translationInput !== "" && sentenceInput !== "") {
        const index = getRowIndex();

        let tbody = document.querySelector('tbody');
        let rows = tbody.querySelectorAll('tr');

        for (let i = 0; i < rows.length; i++) {
            let row = rows[i];
            let th = row.querySelector('th');

            if (Number(th.textContent) === index) {
                let tds = row.querySelectorAll('td');

                for (let j = 0; j < tds.length; j++) {
                    tds[j].textContent = arr[j];
                }
                break; // Exit the loop once we've found the matching <th>
            }
        }
        
        setSaveChanged();
        closeUpdate();
    } else {
        const warnMessage = document.getElementById('wordUpdateId');
        warnMessage.textContent = "Each field should not be empty"
    }
}

function showNotification() {
    var notification = document.querySelector(".notification");
    notification.classList.remove('hidden');

    setTimeout(function() {
        notification.classList.add('hidden');
    }, 2000);
}

function dropAll() {
    let confirmed = confirm("Are you sure you want to delete your account and data?");

    if (confirmed) {
        fetch('https://localhost:49161/auth/delete', {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    alert("Your account and data were not deleted");
                } else {
                    alert("Your account and data have been deleted.");
                    window.location.reload();
                }
            });

    } else {
        alert("Account deletion canceled.");
    }
}