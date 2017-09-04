<html>
    <body>
        <h3>Hi ${user.loginName}, </h3>

        <div>
            We are happy to notify you that since last digest email we added new articles! <br/>
            <#foreach journal in journals >
                <b>Category:</b> ${journal.category.name} <b>Name:</b>${journal.name} <br/>
            </#foreach>

        </div>

        <br/>

        <div>
            Have a nice day! <br/>
            <b>Your News service<b>
        </div>
    </body>
</html>