async function handleRequest(endpoint, method, params = {}) {
    const url = new URL(endpoint, window.location.origin);
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    const response = await fetch(url, { method });
    return await response.json();
}

async function fetchTasks() {
    const username = document.getElementById('username-get-tasks').value;
    const type = document.getElementById('type').value;
    const result = await handleRequest(`/tasks/${username}`, 'GET', { type });
    document.getElementById('get-tasks-result').innerText = JSON.stringify(result, null, 2);
}

async function markTaskAsWishList() {
    const username = document.getElementById('username-wishlist').value;
    const taskId = document.getElementById('task-id-wishlist').value;
    const result = await handleRequest(`/tasks/${username}/wishList`, 'PUT', { taskId });
    document.getElementById('mark-wishlist-result').innerText = result ? 'Task marked as wish list' : 'Error marking task as wish list';
}

async function markTaskAsCompleted() {
    const username = document.getElementById('username-completed').value;
    const taskId = document.getElementById('task-id-completed').value;
    const result = await handleRequest(`/tasks/${username}/completed`, 'PUT', { taskId });
    document.getElementById('mark-completed-result').innerText = result ? 'Task marked as completed' : 'Error marking task as completed';
}

async function fetchWishList() {
    const username = document.getElementById('username-wishlist-fetch').value;
    const result = await handleRequest(`/tasks/${username}/wishList`, 'GET');
    document.getElementById('wish-list-result').innerText = JSON.stringify(result, null, 2);
}

async function fetchCompletedTasks() {
    const username = document.getElementById('username-completed-fetch').value;
    const result = await handleRequest(`/tasks/${username}/completedTasks`, 'GET');
    document.getElementById('completed-tasks-result').innerText = JSON.stringify(result, null, 2);
}

async function fetchTaskOfTheDay() {
    const result = await handleRequest('/tasks/taskOfTheDay', 'GET');
    document.getElementById('task-of-the-day-result').innerText = JSON.stringify(result, null, 2);
}

async function fetchRatedTask() {
    const result = await handleRequest('/tasks/ratedTask', 'GET');
    document.getElementById('rated-task-result').innerText = JSON.stringify(result, null, 2);
}
