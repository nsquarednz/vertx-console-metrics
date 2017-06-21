import Overview from './Overview.vue';

if (!window.vertxConsoleRoutes) window.vertxConsoleRoutes = [];
window.vertxConsoleRoutes.push({
    path: '/metrics',
    component: Overview
});