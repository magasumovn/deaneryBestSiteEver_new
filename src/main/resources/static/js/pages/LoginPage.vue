<template>
    <v-card
            class="mx-auto mt-10"
            max-width="400"
    >
        <v-card-title>
            <span class="headline">Вход</span>
        </v-card-title>
        <form action="/login" method="post">
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field
                                    name="username"
                                    label="login"
                                    required
                            />
                        </v-col>
                        <v-col cols="12">
                            <v-text-field
                                    name="password"
                                    label="Password"
                                    type="password"
                                    required
                            />
                        </v-col>
                    </v-row>
                </v-container>
            </v-card-text>
            <v-card-actions>
                <div class="text-center ml-5 mt-n7">
                    <v-btn color="primary" dark type="submit">Войти</v-btn>
                </div>
            </v-card-actions>
        </form>
    </v-card>
</template>

<script>
    export default {
        name: "LoginPage",
    }
</script>

<style scoped>

</style>