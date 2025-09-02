const API = axios.create({
    baseURL: location.origin,
    timeout: 3000,
    raxConfig: {
        retry: 3,
        retryDelay: 1000,
    },
});

const mdCvter = new showdown.Converter();

const showLoading = () => {
    let elem = document.getElementById("loading");
    elem.classList.remove("d-none");
    elem.animate([{ opacity: 0 }, { opacity: 1 }], { duration: 100 });
};

const hideLoading = () => {
    let elem = document.getElementById("loading");
    elem.animate([{ opacity: 1 }, { opacity: 0 }], { duration: 500 }).onfinish = () => {
        elem.classList.add("d-none");
    };
};

const parseList = (list) => {
    if (list.total === 0) displayPage("404");

    let cardsList = document.getElementById("cards-list");
    let cardSample = document.getElementById("card-sample");

    cardsList.innerHTML = "";

    for (let sight of list.results) {
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

        for (let [cls, href] of [
            ["card-title", `#/${sight.district.slice(0, -1)}/${sight.id}`],
            ["card-address", sight.mapUrl],
            ["card-source", sight.sourceUrl],
        ]) {
            card.getElementsByClassName(cls)[0].href = href;
        }

        card.getElementsByClassName("card-img-top")[0].src = sight.photoUrl || "/image/blank.jpg";
        card.getElementsByClassName("card-img-top")[0].alt = sight.name;
    }
};

const parseSight = (sight) => {
    let districtInUrl = decodeURIComponent(location.hash).split("/")[1];

    // 景點與網址中的行政區不符合
    if (!sight.district.includes(districtInUrl)) {
        displayPage("404");
        return;
    }

    let img = document.createElement("img");
    img.src = sight.photoUrl || "/image/blank.jpg";
    img.alt = sight.name;

    let elem = document.querySelector("#sight-carousel .carousel-item:first-child");
    elem.innerHTML = "";
    elem.appendChild(img);
    document.querySelector("#sight-carousel .carousel-indicators button:first-child").click();

    document.getElementById("sight-category").innerText = sight.category;
    document.getElementById("sight-address").innerText = sight.address;
    document.getElementById("sight-address").href = sight.mapUrl;
    document.getElementById("sight-title").innerText = sight.name;
    document.getElementById("sight-description").innerHTML = mdCvter.makeHtml(sight.description);
    document.getElementById("sight-source").href = sight.sourceUrl;
};

const displayPage = (page, ...args) => {
    // 隱藏所有頁面
    document.querySelectorAll(".page").forEach((el) => el.classList.add("d-none"));
    if (!page) return;

    // 顯示指定頁面
    document.getElementById(page).classList.remove("d-none");

    let url, func;

    // 設定頁面內容
    if (page === "list") {
        if (args[0].endsWith("區")) args[0] = args[0].slice(0, -1); // 移除「區」字
        document.querySelector("#list h1").innerText = `${args[0]}區ㄉ觀光景點！`;
        url = "/sights?district=" + encodeURIComponent(args[0]);
        func = parseList;
    } else if (page === "sight") {
        url = "/sight/" + encodeURIComponent(args[0]);
        func = parseSight;
    } else if (page === "404" && args[0]) {
        document.getElementById("error-detail").innerText = args[0];
    }

    // 載入資料
    if (url && func) {
        showLoading();
        API.get(url)
            .then(function (response) {
                func(response.data);
            })
            .catch(function (error) {
                let detail;
                if (!error.response || !(error.response.status === 404 || error.response.status === 422)) {
                    console.log(error);
                    detail = error.message;
                }
                displayPage("404", detail);
            })
            .finally(function () {
                setTimeout(hideLoading, 200);
            });
    }
};

const resolveHashRoute = (path) => {
    path = decodeURIComponent(path || location.hash.slice(1)).split("/");
    console.log("Resolve route path:", JSON.stringify(path));
    console.log(path.length);

    if (path[0] !== "") {
        // 錯誤的路由格式 (不是以 #/ 開頭)
        displayPage("404");
    } else if (path.length === 1 || (path.length === 2 && path[1] === "")) {
        // 首頁
        displayPage("index");
    } else if (path.length === 2) {
        displayPage("list", path[1]);
    } else if (path.length === 3) {
        displayPage("sight", path[2]);
    } else {
        displayPage("404");
    }
};

(() => {
    document.querySelectorAll("#loading span").forEach((el, index) => {
        el.style.animationDelay = `${index * 0.1}s`;
    });

    resolveHashRoute();

    window.addEventListener("hashchange", () => {
        resolveHashRoute();
    });
})();
