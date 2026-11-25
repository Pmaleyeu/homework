pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response body fields match expected values", function () {
    const body = pm.response.json();

    pm.expect(Object.keys(body).sort()).to.deep.equal(
        ["args", "data", "files", "form", "headers", "json", "url"].sort()
    );

    pm.expect(body.args).to.deep.equal({});
    pm.expect(body.data).to.deep.equal({});
    pm.expect(body.files).to.deep.equal({});
    pm.expect(body.form).to.deep.equal({
        foo1: "bar1",
        foo2: "bar2"
    });
    pm.expect(body.json).to.equal(null);
    pm.expect(body.url).to.equal("https://postman-echo.com/post");

    pm.expect(body.headers.host).to.equal("postman-echo.com");
    pm.expect(body.headers["content-type"]).to.include(
        "application/x-www-form-urlencoded"
    );
});