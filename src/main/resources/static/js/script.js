const API = axios.create({
    baseURL: "http://localhost:5050",
    timeout: 3000,
    raxConfig: {
        retry: 3,
        retryDelay: 1000,
    },
});

const showLoading = () => {
    let elem = document.getElementById("loading");
    elem.classList.remove("d-none");
    elem.animate([{ opacity: 0 }, { opacity: 1 }], { duration: 500 });
};

const hideLoading = () => {
    let elem = document.getElementById("loading");
    elem.animate([{ opacity: 1 }, { opacity: 0 }], { duration: 500 }).onfinish = () => {
        elem.classList.add("d-none");
    };
};

const hideOtherPage = (page) => {
    document.querySelectorAll(".page").forEach((el) => el.classList.add("d-none"));
    document.getElementById(page).classList.remove("d-none");
};

const showIndex = () => {
    hideOtherPage("index");
};

const show404 = () => {
    hideOtherPage("404");
};

const showListPage = (district) => {
    hideOtherPage("list");
    showLoading();

    document.querySelector("#list h1").innerText = `${district}區ㄉ觀光景點！`;

    API.get("/sights", { params: { district: district } })
        .then(function (response) {
            let data = response.data;
            if (data.total === 0) show404();
            let cardsList = document.getElementById("cards-list");
            let cardSample = document.getElementById("card-sample");

            cardsList.innerHTML = "";

            for (let sight of data.results) {
                let card = cardSample.cloneNode(true);
                cardsList.appendChild(card);
                card.classList.remove("d-none");

                for (let [cls, attr] of [
                    ["card-title", "name"],
                    ["card-category", "category"],
                    ["card-text", "description"],
                    ["card-address", "address"],
                ]) {
                    card.getElementsByClassName(cls)[0].innerText = sight[attr];
                }

                card.getElementsByClassName("card-img-top")[0].src = sight.photoUrl || "/image/blank.jpg";
                card.getElementsByClassName("card-img-top")[0].alt = sight.name;

                card.getElementsByClassName("card-title")[0].href = `#/${sight.district}/${sight.id}`;
                card.getElementsByClassName("card-address")[0].href = sight.mapUrl;
                card.getElementsByClassName("card-source")[0].href = sight.sourceUrl;
            }
        })
        .catch(function (error) {
            console.log(error);
            show404();
            document.getElementById("error-detail").textContent = error.message;
        })
        .finally(function () {
            hideLoading();
        });
};

const showSightPage = (sightID) => {
    console.log("Show sight page for sight ID:", sightID);
    hideOtherPage("sight");
    showLoading();

    API.get("/sight/" + sightID)
        .then(function (response) {
            let data = response.data;

            let img = document.querySelector("#sight-carousel .carousel-item:first-child img");
            img.src = data.photoUrl || "/image/blank.jpg";
            img.alt = data.name;

            document.getElementById("sight-category").innerText = data.category;
            document.getElementById("sight-address").innerText = data.address;
            document.getElementById("sight-address").href = data.mapUrl;
            document.getElementById("sight-title").innerText = data.name;
            document.getElementById("sight-description").innerHTML = new showdown.Converter().makeHtml(
                data.description
            );
        })
        .catch(function (error) {
            show404();
            if (!error.response || !(error.response.status === 404 || error.response.status === 422)) {
                console.log(error);
                document.getElementById("error-detail").innerText = error.message;
            }
        })
        .finally(function () {
            hideLoading();
        });
};

const route = (path) => {
    let paths = decodeURI(path).split("/");
    console.log("Route path:", decodeURI(path));

    // 變更導航列的 active 狀態
    document.querySelector(".nav-link.active")?.classList.remove("active");
    document.getElementById("nav-" + (paths[1] || "home"))?.classList.add("active");

    if (paths.length == 1 || !paths[1]) {
        showIndex();
    } else if (paths.length == 2 && paths[1]) {
        showListPage(paths[1]);
    } else if (paths.length == 3 && paths[2]) {
        showSightPage(paths[2]);
    } else {
        show404();
    }
};

(() => {
    document.querySelectorAll("#loading span").forEach((el, index) => {
        el.style.animationDelay = `${index * 0.1}s`;
    });

    route(location.hash.slice(1));

    navigation.addEventListener("navigate", (event) => {
        route(event.destination.url.split("#")[1]);
    });
})();
