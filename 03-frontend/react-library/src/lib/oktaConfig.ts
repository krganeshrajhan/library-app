export const oktaConfig = {
    clientId: '0oan4mthe1QQDRb9r5d7',
    issuer: 'https://dev-68708296.okta.com/oauth2/default',
    redirectUri: 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}