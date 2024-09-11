package devandroid.sofia.loginpage.utils

import EditFormScreen
import FormScreen
import HomeScreen
import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import devandroid.sofia.loginpage.CardDetailScreen
import devandroid.sofia.loginpage.TrechoScreen
import devandroid.sofia.loginpage.MapScreen
import devandroid.sofia.loginpage.data.Chamado



@Composable
fun NavigationController() {
    val navController = rememberNavController()
    val cards = remember { mutableStateOf(listOf<Chamado>()) }

    // Função para adicionar um novo cartão
    val addCard: (Chamado) -> Unit = { chamado ->
        val updatedCards = cards.value.toMutableList()
        updatedCards.add(chamado)
        cards.value = updatedCards
    }

    // Função para atualizar um cartão existente
    val updateCard: (Chamado) -> Unit = { updatedCard ->
        cards.value = cards.value.map { card ->
            if (card.name == updatedCard.name) { // Use a propriedade apropriada
                updatedCard
            } else {
                card
            }
        }
    }

    // Configuração de navegação
    NavHost(navController = navController, startDestination = Route.Login.route) {
        composable(Route.Trecho.route) {
            TrechoScreen(navController)
        }
        composable(Route.Login.route) {
            LoginScreen(navController)
        }

        composable(
            route = Route.Home.routeWithArgs,
            arguments = listOf(navArgument("selectedOption") { defaultValue = "" })
        ) { backStackEntry ->
            val selectedOption = backStackEntry.arguments?.getString("selectedOption").orEmpty()
            HomeScreen(navController, cards, selectedOption)
        }

        composable(Route.Form.route) {
            FormScreen(
                navController = navController,
                addCard = addCard
            )
        }

        composable(
            route = Route.CardDetail.routeWithArgs,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }, // Adicionando o parâmetro 'id'
                navArgument("status") { type = NavType.StringType },
                navArgument("priority") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType },
                navArgument("nameProject") { type = NavType.StringType }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "android-app://devandroid.sofia.loginpage/card_detail/{id}/{nameProject}/{status}/{priority}/{type}"
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val status = backStackEntry.arguments?.getString("status").orEmpty()
            val priority = backStackEntry.arguments?.getString("priority").orEmpty()
            val type = backStackEntry.arguments?.getString("type").orEmpty()
            val nameProject = backStackEntry.arguments?.getString("nameProject").orEmpty()

            println("CardDetailScreen - ID: $id, Status: $status, Priority: $priority, Type: $type, NameProject: $nameProject")

            val chamado = cards.value.find { it.name == nameProject } ?: Chamado(id, nameProject, status, priority, type)

            CardDetailScreen(
                navController = navController,
                onEditClick = {
                    navController.navigate(
                        Route.FormEdit.withArgs(
                            "id" to chamado.id,
                            "nameProject" to chamado.name,
                            "status" to chamado.status,
                            "priority" to chamado.priority,
                            "type" to chamado.type
                        )
                    )
                },
                chamado = chamado
            )
        }

        composable(
            route = Route.FormEdit.routeWithArgs,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("nameProject") { type = NavType.StringType },
                navArgument("status") { type = NavType.StringType },
                navArgument("priority") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val nameProject = backStackEntry.arguments?.getString("nameProject").orEmpty()
            val status = backStackEntry.arguments?.getString("status").orEmpty()
            val priority = backStackEntry.arguments?.getString("priority").orEmpty()
            val type = backStackEntry.arguments?.getString("type").orEmpty()

            println("EditFormScreen - ID: $id, NameProject: $nameProject, Status: $status, Priority: $priority, Type: $type")

            val chamado = Chamado(id, nameProject, status, priority, type)

            EditFormScreen(
                navController = navController,
                chamado = chamado,
                updateCard = { updatedChamado ->
                    updateCard(updatedChamado)
                    navController.popBackStack() // Voltar para a tela anterior após a atualização
                }
            )
        }

        composable(Route.Map.route) {
            MapScreen(navController)
        }
    }
}

object Route {
    val Trecho = RouteItem("trecho")
    val Login = RouteItem("login")
    val Home = RouteItem("home/{selectedOption}")
    val Form = RouteItem("form")
    val FormEdit = RouteItem("form_edit/{id}/{nameProject}/{status}/{priority}/{type}")
    val Map = RouteItem("map")
    val CardDetail = RouteItem("card_detail/{id}/{nameProject}/{status}/{priority}/{type}")

    data class RouteItem(val route: String) {
        fun withArgs(vararg args: Pair<String, String>): String {
            var result = route
            args.forEach { (key, value) ->
                result = result.replace("{$key}", value)
            }
            return result
        }

        val routeWithArgs: String
            get() = route
    }
}
