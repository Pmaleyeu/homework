pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response body fields match expected values", function () {
    const body = pm.response.json();

    pm.expect(Object.keys(body).sort()).to.deep.equal(
        ["args", "headers", "url"].sort()
    );

    pm.expect(body.args).to.deep.equal({
        foo1: "bar1",
        foo2: "bar2"
    });

    pm.expect(body.url).to.equal(
        "https://postman-echo.com/get?foo1=bar1&foo2=bar2"
    );

    pm.expect(body.headers.host).to.equal("postman-echo.com");
    pm.expect(body.headers["x-forwarded-proto"]).to.equal("https");
});