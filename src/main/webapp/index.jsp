<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<header>
			<h1>
				<span>XXX</span>
			</h1>
			<nav>
				<ul>
					<li id="actual"><a href="Controller">Home</a></li>
					<li><a href="Controller?command=Overview">Overview</a></li>
					<li><a href="Controller?command=Register">Register</a></li>
				</ul>
			</nav>
			<h2>Home</h2>

		</header>
		<c:choose>
			<c:when test="${not empty requestcookie}">
				<p>hello ${requestcookie}</p>
				<form action="Controller?command=logout" method="post">
					<p><input type="submit" id="logOut" value="Log Out"></p>
				</form>
			</c:when>
			<c:otherwise>
				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<ul>

							<li>${error}</li>


						</ul>
					</div>
				</c:if>

				<form method="post" action="Controller?command=login" novalidate="novalidate">
					<p><label for="userid">User id</label><input type="text" id="userid" name="userid"
																 required > </p>
					<p><label for="password">Password</label><input type="password" id="password"  name="password" required > </p>
					<p><input type="submit" id="logIn" value="Log In"></p>
				</form>
			</c:otherwise>

		</c:choose>

		<main> Sed ut perspiciatis unde omnis iste natus error sit
		voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque
		ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae
		dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
		aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos
		qui ratione voluptatem sequi nesciunt. </main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>