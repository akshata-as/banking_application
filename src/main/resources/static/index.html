/* ─────────────────────────────────────────────────────────────
   VaultX Pro — Banking Portal
   app.js  (all application logic)
   ───────────────────────────────────────────────────────────── */

const BASE      = 'https://banking-application-2-m8gm.onrender.com/api/accounts';
const USER_BASE = 'https://banking-application-2-m8gm.onrender.com/api/users';

let allAccounts  = [];
let txnLog       = [];
let currentFilter = 'all';
let currentPage   = 1;
const PAGE_SIZE   = 8;
let barChart, doughnutChart;
let currentUser   = null;

const PAGES = {
  dashboard:          ['Dashboard',       'Welcome back — VaultX Banking Portal'],
  'all-accounts':     ['All Accounts',    'Complete list with search & filters'],
  'txn-log':          ['Transaction Log', 'All operations this session'],
  create:             ['Create Account',  'POST /api/accounts'],
  fetch:              ['Fetch Account',   'GET /api/accounts/{id}'],
  'my-account':       ['My Account',      'Your account details'],
  deposit:            ['Deposit Funds',   'PUT /api/accounts/{id}/deposit'],
  withdraw:           ['Withdraw Funds',  'PUT /api/accounts/{id}/withdraw'],
  delete:             ['Delete Account',  'DELETE /api/accounts/{id}'],
  'account-control':  ['Account Control', 'Block / Unblock / Min Balance'],
};

const ROLE_ACCESS = {
  admin:   ['dashboard','all-accounts','txn-log','create','fetch','deposit','withdraw','delete','account-control'],
  manager: ['dashboard','all-accounts','txn-log','fetch','deposit','withdraw','account-control'],
  user:    ['my-account'],
};

/* ── Navigation ─────────────────────────────────────────────── */

document.querySelectorAll('.sb-item').forEach(btn => {
  btn.addEventListener('click', () => goPanel(btn.dataset.panel));
});

function goPanel(name) {
  document.querySelectorAll('.panel').forEach(p  => p.classList.remove('active'));
  document.querySelectorAll('.sb-item').forEach(b => b.classList.remove('active'));

  const panel = document.getElementById('panel-' + name);
  if (panel) panel.classList.add('active');

  const nb = document.querySelector('[data-panel="' + name + '"]');
  if (nb) nb.classList.add('active');

  const [t, c] = PAGES[name] || ['', ''];
  document.getElementById('pg-title').textContent = t;
  document.getElementById('pg-crumb').textContent = c;

  if (name === 'all-accounts') loadAllAccounts();
  if (name === 'dashboard')    loadDashboard();
  if (name === 'my-account' && currentUser && currentUser.accountId)
    loadMyAccount(currentUser.accountId);

  if (currentUser) {
    if (currentUser.role === 'user') setTimeout(() => lockUserFields(currentUser), 50);
    else                             setTimeout(() => unlockFields(), 50);
  }
}

/* ── API helper ─────────────────────────────────────────────── */

async function api(method, url, body) {
  const opts = { method, headers: { 'Content-Type': 'application/json' } };
  if (body) opts.body = JSON.stringify(body);
  const res  = await fetch(url, opts);
  const text = await res.text();
  let data;
  try { data = JSON.parse(text); } catch { data = text; }
  if (!res.ok) throw new Error(typeof data === 'object' ? (data.message || JSON.stringify(data)) : data);
  return data;
}

function v(id) { return document.getElementById(id).value.trim(); }

/* ── Transaction log ────────────────────────────────────────── */

function addTxn(type, label, amount, accountId) {
  txnLog.unshift({ type, label, amount, accountId, time: new Date() });
  document.getElementById('s-txns').textContent = txnLog.length;
  renderTxnLog();
}

function renderTxnLog() {
  const w = document.getElementById('txn-wrap');
  if (!txnLog.length) {
    w.innerHTML = '<div class="txn-empty"><div>📋</div><p>No transactions yet this session.</p></div>';
    return;
  }
  const icons = { create:'➕', fetch:'🔍', deposit:'💰', withdraw:'💸', delete:'🗑️' };
  const cols  = { create:'ic-teal', fetch:'ic-green', deposit:'ic-amber', withdraw:'ic-red', delete:'ic-navy' };
  const ac    = { deposit:'credit', withdraw:'debit', create:'neutral', fetch:'neutral', delete:'neutral' };

  w.innerHTML = '<div class="txn-list">' + txnLog.map(t => `
    <div class="txn-item">
      <div class="txn-icon ${cols[t.type]}">${icons[t.type]}</div>
      <div class="txn-info">
        <div class="action">${t.label}</div>
        <div class="meta">Account #${t.accountId} · ${t.time.toLocaleTimeString()}</div>
      </div>
      <div>
        <div class="txn-amt ${ac[t.type]}">
          ${t.amount
            ? (t.type === 'deposit' ? '+' : t.type === 'withdraw' ? '-' : '') + '₹' + fmtINR(t.amount)
            : '—'}
        </div>
        <div class="txn-time">${t.time.toLocaleDateString()}</div>
      </div>
    </div>`).join('') + '</div>';
}

function clearLog() {
  txnLog = [];
  renderTxnLog();
  toast('Transaction log cleared', 'info');
}

/* ── Render helpers ─────────────────────────────────────────── */

function renderAccount(wid, data, label) {
  document.getElementById(wid).innerHTML = `
    <div class="result-card ok">
      <div class="rc-head"><span>${label}</span><span class="pill pill-ok">200 OK</span></div>
      <div class="rc-body">
        <div class="acct-grid">
          <div class="af"><div class="k">Account ID</div><div class="v">#${data.id}</div></div>
          <div class="af"><div class="k">Account Number</div><div class="v">${data.accountNumber}</div></div>
          <div class="af"><div class="k">Holder Name</div><div class="v">${data.accountHolderName}</div></div>
          <div class="af balf"><div class="k">Current Balance</div><div class="v">₹ ${fmtINR(data.balance)}</div></div>
          <div class="af"><div class="k">Minimum Balance</div><div class="v">₹ ${fmtINR(data.minimumBalance || 500)}</div></div>
          <div class="af"><div class="k">Account Status</div>
            <div class="v" style="font-weight:700;color:${data.blocked ? 'var(--red)' : 'var(--green)'}">
              ${data.blocked ? '🔒 BLOCKED' : '✅ ACTIVE'}
              ${data.blockedReason
                ? `<div style="font-size:.75rem;color:var(--sub);font-weight:400;margin-top:3px;">${data.blockedReason}</div>`
                : ''}
            </div>
          </div>
        </div>
      </div>
    </div>`;
  toast('✓ ' + label, 'ok');
}

function renderMsg(wid, msg) {
  document.getElementById(wid).innerHTML = `
    <div class="result-card ok">
      <div class="rc-head"><span>Success</span><span class="pill pill-ok">200 OK</span></div>
      <div class="rc-body"><div class="res-msg">${msg}</div></div>
    </div>`;
  toast('✓ ' + msg, 'ok');
}

function renderErr(wid, err) {
  document.getElementById(wid).innerHTML = `
    <div class="result-card err">
      <div class="rc-head"><span>Error</span><span class="pill pill-err">Failed</span></div>
      <div class="rc-body"><div class="res-msg err">${err.message || err}</div></div>
    </div>`;
  toast('✗ ' + (err.message || err), 'err');
}

/* ── Dashboard ──────────────────────────────────────────────── */

async function loadDashboard() {
  try {
    const data = await api('GET', BASE);
    allAccounts = data;
    const total = data.reduce((s, a) => s + a.balance, 0);
    document.getElementById('s-total').textContent   = data.length;
    document.getElementById('s-bal').textContent     = '₹' + fmtShort(total);
    document.getElementById('s-avg').textContent     = data.length ? '₹' + fmtShort(total / data.length) : '₹0';
    document.getElementById('acct-tag').textContent  = data.length + ' Accounts';
    renderRecentTable(data.slice(-5).reverse());
    renderCharts(data);
  } catch (e) { /* silent on dashboard load error */ }
}

function renderRecentTable(accounts) {
  const tb = document.getElementById('recent-tbody');
  if (!accounts.length) {
    tb.innerHTML = '<tr><td colspan="4"><div class="empty-state"><div>🏦</div><p>No accounts yet.</p></div></td></tr>';
    return;
  }
  tb.innerHTML = accounts.map(a => {
    const name = a.accountHolderName || 'Unknown';
    const num  = a.accountNumber || '—';
    const bal  = a.balance || 0;
    return `
    <tr>
      <td><div style="display:flex;align-items:center;gap:10px;">
        <div class="avatar" style="background:${aColor(name)}">${name[0].toUpperCase()}</div>
        <span style="font-weight:700">${name}</span>
      </div></td>
      <td class="mono">${num}</td>
      <td class="bal ${bal < 1000 ? 'low' : ''}">
        ₹${fmtINR(bal)}
        ${a.blocked
          ? '<span style="margin-left:6px;font-size:.7rem;background:#fee2e2;color:var(--red);padding:1px 6px;border-radius:10px;font-weight:700;">BLOCKED</span>'
          : ''}
      </td>
      <td><div class="row-actions">
        <button class="row-btn" onclick="quickFetch(${a.id})">View</button>
        <button class="row-btn" onclick="quickDeposit(${a.id})">Deposit</button>
      </div></td>
    </tr>`;
  }).join('');
}

function renderCharts(data) {
  if (!data.length) return;

  if (barChart) barChart.destroy();
  barChart = new Chart(document.getElementById('barChart'), {
    type: 'bar',
    data: {
      labels:   data.map(a => a.accountHolderName.split(' ')[0]),
      datasets: [{
        label:           'Balance (₹)',
        data:            data.map(a => a.balance),
        backgroundColor: 'rgba(8,145,178,0.7)',
        borderColor:     '#0891b2',
        borderWidth:     2,
        borderRadius:    8,
      }],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: { legend: { display: false } },
      scales: {
        y: {
          ticks: { callback: v => '₹' + fmtShort(v), font: { family: 'IBM Plex Mono', size: 10 } },
          grid:  { color: '#f1f5f9' },
        },
        x: {
          ticks: { font: { family: 'Nunito', size: 11 } },
          grid:  { display: false },
        },
      },
    },
  });

  const ranges = [
    data.filter(a => a.balance >= 10000).length,
    data.filter(a => a.balance >= 1000 && a.balance < 10000).length,
    data.filter(a => a.balance < 1000).length,
  ];

  if (doughnutChart) doughnutChart.destroy();
  doughnutChart = new Chart(document.getElementById('doughnutChart'), {
    type: 'doughnut',
    data: {
      labels:   ['High (>10k)', 'Mid (1k-10k)', 'Low (<1k)'],
      datasets: [{
        data:            ranges,
        backgroundColor: ['#059669', '#d97706', '#dc2626'],
        borderWidth:     0,
        hoverOffset:     6,
      }],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'bottom', labels: { font: { family: 'Nunito', size: 11 }, padding: 14 } },
      },
      cutout: '65%',
    },
  });
}

/* ── All Accounts ───────────────────────────────────────────── */

async function loadAllAccounts() {
  try {
    const data = await api('GET', BASE);
    allAccounts = data;
    document.getElementById('acct-count-lbl').textContent = data.length + ' total accounts found';
    document.getElementById('acct-tag').textContent        = data.length + ' Accounts';

    const si = document.getElementById('search-inp');
    if (si) si.value = '';

    currentFilter = 'all';
    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
    const firstBtn = document.querySelector('.filter-btn');
    if (firstBtn) firstBtn.classList.add('active');

    currentPage = 1;
    renderTable();
  } catch (e) {
    document.getElementById('all-tbody').innerHTML =
      `<tr><td colspan="5"><div class="empty-state"><div>❌</div><p>Error: ${e.message}</p></div></td></tr>`;
  }
}

function getFiltered() {
  const q = (document.getElementById('search-inp')?.value || '').trim().toLowerCase();
  return allAccounts.filter(a => {
    const ms = !q
      || (a.accountHolderName && a.accountHolderName.toLowerCase().includes(q))
      || (a.accountNumber     && a.accountNumber.toLowerCase().includes(q))
      || (a.id                && String(a.id).includes(q));
    const mf = currentFilter === 'all'
      || (currentFilter === 'high'    && a.balance >= 10000)
      || (currentFilter === 'mid'     && a.balance >= 1000 && a.balance < 10000)
      || (currentFilter === 'low'     && a.balance < 1000)
      || (currentFilter === 'blocked' && a.blocked === true);
    return ms && mf;
  });
}

function filterTable() {
  currentPage = 1;
  if (allAccounts.length === 0) { loadAllAccounts(); return; }
  renderTable();
}

function setFilter(f, btn) {
  currentFilter = f;
  document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
  btn.classList.add('active');
  currentPage = 1;
  renderTable();
}

function renderTable() {
  const filtered = getFiltered();
  const total    = filtered.length;
  const pages    = Math.ceil(total / PAGE_SIZE) || 1;
  const start    = (currentPage - 1) * PAGE_SIZE;
  const slice    = filtered.slice(start, start + PAGE_SIZE);

  document.getElementById('page-info').textContent =
    `Showing ${Math.min(start + 1, total)}–${Math.min(start + PAGE_SIZE, total)} of ${total}`;

  const tb = document.getElementById('all-tbody');
  if (!slice.length) {
    tb.innerHTML = '<tr><td colspan="5"><div class="empty-state"><div>🔍</div><p>No accounts match your search.</p></div></td></tr>';
  } else {
    tb.innerHTML = slice.map((a, i) => {
      const name = a.accountHolderName || 'Unknown';
      const num  = a.accountNumber || '—';
      const bal  = a.balance || 0;
      return `
      <tr>
        <td class="mono">${start + i + 1}</td>
        <td><div style="display:flex;align-items:center;gap:10px;">
          <div class="avatar" style="background:${aColor(name)}">${name[0].toUpperCase()}</div>
          <div>
            <div style="font-weight:700">${name}</div>
            <div style="font-size:.72rem;color:var(--sub)">ID #${a.id}</div>
          </div>
        </div></td>
        <td class="mono">${num}</td>
        <td class="bal ${bal < 1000 ? 'low' : ''}">
          ₹${fmtINR(bal)}
          ${a.blocked
            ? '<span style="margin-left:6px;font-size:.7rem;background:#fee2e2;color:var(--red);padding:1px 6px;border-radius:10px;font-weight:700;">BLOCKED</span>'
            : ''}
        </td>
        <td><div class="row-actions">
          <button class="row-btn" onclick="quickFetch(${a.id})">View</button>
          <button class="row-btn" onclick="quickDeposit(${a.id})">Deposit</button>
          <button class="row-btn" onclick="quickWithdraw(${a.id})">Withdraw</button>
          <button class="row-btn" onclick="quickControl(${a.id})"
            style="background:${a.blocked ? '#fee2e2' : '#f0fdf4'};color:${a.blocked ? 'var(--red)' : 'var(--green)'};border-color:${a.blocked ? '#fecaca' : '#bbf7d0'}">
            ${a.blocked ? '🔒 Blocked' : '🛡️ Manage'}
          </button>
          <button class="row-btn danger" onclick="quickDelete(${a.id})">Delete</button>
        </div></td>
      </tr>`;
    }).join('');
  }

  const pb = document.getElementById('page-btns');
  pb.innerHTML = '';
  for (let p = 1; p <= pages; p++) {
    const b = document.createElement('button');
    b.className  = 'page-btn' + (p === currentPage ? ' active' : '');
    b.textContent = p;
    b.onclick = () => { currentPage = p; renderTable(); };
    pb.appendChild(b);
  }
}

/* Quick-nav helpers */
function quickFetch(id)    { document.getElementById('g-id').value    = id; goPanel('fetch');           getAccount(); }
function quickDeposit(id)  { document.getElementById('dep-id').value  = id; goPanel('deposit'); }
function quickWithdraw(id) { document.getElementById('wth-id').value  = id; goPanel('withdraw'); }
function quickDelete(id)   { document.getElementById('d-id').value    = id; goPanel('delete'); }
function quickControl(id)  {
  document.getElementById('ctrl-id').value = id;
  document.getElementById('mb-id').value   = id;
  goPanel('account-control');
}

/* ── CRUD Operations ────────────────────────────────────────── */

async function createAccount() {
  const name   = v('c-name');
  const num    = v('c-num');
  const bal    = v('c-bal');
  const minbal = v('c-minbal');
  if (!name || !num) { toast('Name & Account Number required', 'err'); return; }
  try {
    const d = await api('POST', BASE, {
      accountHolderName: name,
      accountNumber:     num,
      balance:           parseFloat(bal)    || 0,
      minimumBalance:    parseFloat(minbal) || 500,
    });
    renderAccount('res-create', d, 'Account Created');
    if (d.blocked) toast('⚠️ Account created but BLOCKED — balance below minimum', 'err');
    addTxn('create', 'Account Created — ' + d.accountHolderName, null, d.id);
    ['c-name','c-num','c-bal','c-minbal'].forEach(id => document.getElementById(id).value = '');
    loadDashboard();
  } catch (e) { renderErr('res-create', e); }
}

async function getAccount() {
  const id = v('g-id');
  if (!id) { toast('Enter an account ID', 'err'); return; }
  if (!validateUserAccess('g-id')) return;
  try {
    const d = await api('GET', `${BASE}/${id}`);
    renderAccount('res-fetch', d, 'Account Found');
    addTxn('fetch', 'Fetched — ' + d.accountHolderName, null, d.id);
  } catch (e) { renderErr('res-fetch', e); }
}

async function deposit() {
  const id  = v('dep-id');
  const amt = v('dep-amt');
  if (!id || !amt) { toast('ID and Amount required', 'err'); return; }
  if (!validateUserAccess('dep-id')) return;
  try {
    const d = await api('PUT', `${BASE}/${id}/deposit?amount=${amt}`);
    renderAccount('res-deposit', d, `Deposited ₹${fmtINR(amt)}`);
    addTxn('deposit', 'Deposit — ' + d.accountHolderName, parseFloat(amt), d.id);
    document.getElementById('dep-amt').value = '';
    if (currentUser && currentUser.role !== 'user') loadDashboard();
  } catch (e) { renderErr('res-deposit', e); }
}

async function withdraw() {
  const id  = v('wth-id');
  const amt = v('wth-amt');
  if (!id || !amt) { toast('ID and Amount required', 'err'); return; }
  if (!validateUserAccess('wth-id')) return;
  try {
    const d = await api('PUT', `${BASE}/${id}/withdraw?amount=${amt}`);
    renderAccount('res-withdraw', d, `Withdrawn ₹${fmtINR(amt)}`);
    addTxn('withdraw', 'Withdrawal — ' + d.accountHolderName, parseFloat(amt), d.id);
    document.getElementById('wth-amt').value = '';
    if (currentUser && currentUser.role !== 'user') loadDashboard();
  } catch (e) { renderErr('res-withdraw', e); }
}

async function deleteAccount() {
  const id = v('d-id');
  if (!id) { toast('Enter an account ID', 'err'); return; }
  if (!confirm(`Permanently delete account #${id}? This cannot be undone.`)) return;
  try {
    const msg = await api('DELETE', `${BASE}/${id}`);
    renderMsg('res-delete', msg);
    addTxn('delete', 'Account Deleted #' + id, null, id);
    document.getElementById('d-id').value = '';
    loadDashboard();
  } catch (e) { renderErr('res-delete', e); }
}

async function blockAccount() {
  const id     = v('ctrl-id');
  const reason = v('ctrl-reason');
  if (!id) { toast('Enter an account ID', 'err'); return; }
  try {
    const d = await api('PUT', `${BASE}/${id}/block?reason=${encodeURIComponent(reason || 'Blocked by administrator')}`);
    renderAccount('res-block', d, 'Account Blocked');
    toast('🔒 Account #' + id + ' blocked', 'err');
    loadAllAccounts();
  } catch (e) { renderErr('res-block', e); }
}

async function unblockAccount() {
  const id = v('ub-id');
  if (!id) { toast('Enter an account ID', 'err'); return; }
  try {
    const d = await api('PUT', `${BASE}/${id}/unblock`);
    renderAccount('res-unblock', d, 'Account Unblocked');
    toast('🔓 Account #' + id + ' unblocked', 'ok');
    loadAllAccounts();
  } catch (e) { renderErr('res-unblock', e); }
}

async function updateMinBalance() {
  const id  = v('mb-id');
  const amt = v('mb-amt');
  if (!id || !amt) { toast('Account ID and amount required', 'err'); return; }
  try {
    const d = await api('PUT', `${BASE}/${id}/minimum-balance?amount=${amt}`);
    renderAccount('res-minbal', d, 'Minimum Balance Updated');
    if (d.blocked) toast('⚠️ Account auto-blocked — balance below new minimum', 'err');
    else           toast('✓ Minimum balance updated', 'ok');
    loadAllAccounts();
  } catch (e) { renderErr('res-minbal', e); }
}

/* ── Utilities ──────────────────────────────────────────────── */

function fmtINR(n) {
  return Number(n).toLocaleString('en-IN', { minimumFractionDigits: 2 });
}

function fmtShort(n) {
  if (n >= 100000) return (n / 100000).toFixed(1) + 'L';
  if (n >= 1000)   return (n / 1000).toFixed(1)   + 'K';
  return Number(n).toFixed(0);
}

const PAL = ['#0891b2','#059669','#d97706','#7c3aed','#dc2626','#0d9488','#b45309','#4338ca'];
function aColor(name) {
  let h = 0;
  for (const c of name) h = (h * 31 + c.charCodeAt(0)) % PAL.length;
  return PAL[h];
}

let toastT;
function toast(msg, type) {
  const el = document.getElementById('toast');
  clearTimeout(toastT);
  el.textContent = msg;
  el.className   = 'show ' + type;
  toastT = setTimeout(() => (el.className = ''), 3200);
}

/* ── Role / Access ──────────────────────────────────────────── */

function lockUserFields(user) {
  if (!user || user.role !== 'user') return;
  ['g-id','dep-id','wth-id'].forEach(fieldId => {
    const el = document.getElementById(fieldId);
    if (!el) return;
    el.value = user.accountId;
    el.setAttribute('readonly', true);
    el.style.cssText = 'background:#f0f9ff;color:#0369a1;font-weight:700;cursor:not-allowed;border:1.5px solid #bae6fd;';
    el.title = 'You can only access your own account';
  });
}

function unlockFields() {
  ['g-id','dep-id','wth-id'].forEach(fieldId => {
    const el = document.getElementById(fieldId);
    if (!el) return;
    el.value = '';
    el.removeAttribute('readonly');
    el.style.cssText = '';
    el.title = '';
  });
}

function validateUserAccess(inputId) {
  if (!currentUser || currentUser.role !== 'user') return true;
  const entered = parseInt(document.getElementById(inputId)?.value);
  if (entered !== currentUser.accountId) {
    toast('⛔ You can only access your own account', 'err');
    document.getElementById(inputId).value = currentUser.accountId;
    return false;
  }
  return true;
}

function applyRoleAccess(role) {
  const allowed = ROLE_ACCESS[role] || [];
  document.querySelectorAll('.sb-item').forEach(btn => {
    btn.style.display = allowed.includes(btn.dataset.panel) ? 'flex' : 'none';
  });
  const overviewPanels = ['dashboard','all-accounts','txn-log'];
  const opsPanels      = ['create','fetch','deposit','withdraw','delete','account-control'];
  const sections = document.querySelectorAll('.sb-section');
  if (sections[0]) sections[0].style.display = overviewPanels.some(p => allowed.includes(p)) ? '' : 'none';
  if (sections[1]) sections[1].style.display = opsPanels.some(p => allowed.includes(p))      ? '' : 'none';
}

function updateUserUI(user) {
  const roleLabels = { admin:'Administrator', manager:'Manager', user:'Account Holder' };
  const roleColors = {
    admin:   'linear-gradient(135deg,#0891b2,#22d3ee)',
    manager: 'linear-gradient(135deg,#7c3aed,#a78bfa)',
    user:    'linear-gradient(135deg,#059669,#34d399)',
  };
  document.getElementById('sb-username').textContent    = user.fullName;
  document.getElementById('sb-role-badge').textContent  = roleLabels[user.role] || user.role;
  document.getElementById('sb-avatar').textContent      = user.fullName[0].toUpperCase();
  document.getElementById('sb-avatar').style.background = roleColors[user.role];
  document.getElementById('user-tag').textContent       = '👤 ' + user.fullName;
  document.getElementById('pg-crumb').textContent       = 'Welcome back, ' + user.fullName + ' — VaultX';
}

/* ── Login / Register / Logout ──────────────────────────────── */

function showRegister() {
  document.getElementById('login-view').style.display    = 'none';
  document.getElementById('register-view').style.display = 'block';
  document.getElementById('login-card-title').textContent = 'Create Account';
  document.getElementById('login-card-sub').textContent   = 'Register to get access to VaultX';
}

function showLogin() {
  document.getElementById('login-view').style.display    = 'block';
  document.getElementById('register-view').style.display = 'none';
  document.getElementById('login-card-title').textContent = 'Welcome Back';
  document.getElementById('login-card-sub').textContent   = 'Sign in to access the banking dashboard';
}

async function doRegister() {
  const name  = document.getElementById('r-name').value.trim();
  const uname = document.getElementById('r-user').value.trim().toLowerCase();
  const pass  = document.getElementById('r-pass').value;
  const role  = document.getElementById('r-role').value;
  const err   = document.getElementById('reg-err');
  err.classList.remove('show');
  if (!name || !uname || !pass) { err.textContent = 'All fields are required.'; err.classList.add('show'); return; }
  try {
    const res  = await fetch(USER_BASE + '/register', {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify({ fullName: name, username: uname, password: pass, role }),
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || 'Registration failed');
    showLogin();
    document.getElementById('l-user').value = uname;
    toast('✓ Account created! You can now login.', 'ok');
  } catch (e) {
    err.textContent = e.message;
    err.classList.add('show');
  }
}

async function doLogin() {
  const u   = document.getElementById('l-user').value.trim().toLowerCase();
  const p   = document.getElementById('l-pass').value;
  const err = document.getElementById('login-err');
  err.classList.remove('show');

  if (!u || !p) {
    err.textContent = 'Please enter username and password';
    err.classList.add('show');
    return;
  }

  const loginBtn = document.getElementById('login-btn');
  try {
    if (loginBtn) { loginBtn.disabled = true; loginBtn.innerHTML = 'Signing in...'; }

    const res = await fetch('https://banking-application-2-m8gm.onrender.com/api/users/login', {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify({ username: u, password: p }),
    });

    let data;
    try { data = await res.json(); } catch { throw new Error('Server returned invalid response'); }
    if (!res.ok) throw new Error(data.error || data.message || 'Invalid credentials');

    currentUser = data;
    applyRoleAccess(data.role);
    updateUserUI(data);

    const screen = document.getElementById('login-screen');
    screen.classList.add('hide');

    setTimeout(() => {
      screen.style.display = 'none';
      if (data.role === 'user') {
        goPanel('my-account');
        if (data.accountId) {
          setTimeout(() => loadMyAccount(data.accountId), 300);
        } else {
          document.getElementById('my-balance').textContent = 'No Account';
          document.getElementById('my-name').textContent    = data.fullName || data.username;
          document.getElementById('my-accnum').textContent  = 'Not linked';
          document.getElementById('my-id').textContent      = 'Contact admin';
        }
      } else {
        loadDashboard();
      }
    }, 400);

    toast('✓ Login successful', 'ok');

  } catch (e) {
    err.textContent = e.message;
    err.classList.add('show');
    document.getElementById('l-pass').value = '';
    toast('✗ ' + e.message, 'err');
  } finally {
    if (loginBtn) { loginBtn.disabled = false; loginBtn.innerHTML = 'Sign In →'; }
  }
}

function doLogout() {
  if (!confirm('Are you sure you want to logout?')) return;
  currentUser = null;
  document.getElementById('l-user').value = '';
  document.getElementById('l-pass').value = '';
  document.getElementById('login-err').classList.remove('show');
  showLogin();

  document.querySelectorAll('.sb-item').forEach(b    => (b.style.display = 'flex'));
  document.querySelectorAll('.sb-section').forEach(s => (s.style.display = ''));

  const screen = document.getElementById('login-screen');
  screen.style.display = 'flex';
  screen.classList.remove('hide');
  void screen.offsetHeight; // force reflow to re-enable animation

  document.querySelectorAll('.sb-item').forEach(b => b.classList.remove('active'));
  document.querySelector('[data-panel="dashboard"]').classList.add('active');
  document.getElementById('pg-title').textContent      = 'Dashboard';
  document.getElementById('pg-crumb').textContent      = 'Welcome back — VaultX Banking Portal';
  document.getElementById('user-tag').textContent      = '👤 Admin';
  document.getElementById('sb-username').textContent   = 'Admin';
  document.getElementById('sb-role-badge').textContent = 'Administrator';
  document.getElementById('sb-avatar').textContent     = 'A';
  document.getElementById('sb-avatar').style.background = 'linear-gradient(135deg,#0891b2,#22d3ee)';

  unlockFields();
  document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
  document.getElementById('panel-dashboard').classList.add('active');
}

/* ── My Account ─────────────────────────────────────────────── */

async function loadMyAccount(accountId) {
  if (!accountId) {
    document.getElementById('my-balance').textContent = 'No account linked';
    return;
  }
  document.getElementById('my-balance').textContent = 'Loading...';
  document.getElementById('my-name').textContent    = '—';
  document.getElementById('my-accnum').textContent  = '—';
  document.getElementById('my-id').textContent      = '—';
  try {
    const d = await api('GET', `${BASE}/${accountId}`);
    renderMyAccount(d);
  } catch (e) {
    document.getElementById('my-balance').textContent = 'Error loading';
    toast('Could not load account: ' + e.message, 'err');
  }
}

function renderMyAccount(d) {
  document.getElementById('my-balance').textContent = '₹ ' + fmtINR(d.balance);
  document.getElementById('my-name').textContent    = d.accountHolderName;
  document.getElementById('my-accnum').textContent  = d.accountNumber;
  document.getElementById('my-id').textContent      = '#' + d.id;
}

async function myDeposit() {
  const amt = document.getElementById('my-dep-amt').value.trim();
  if (!amt || !currentUser?.accountId) { toast('Enter amount', 'err'); return; }
  try {
    const d = await api('PUT', `${BASE}/${currentUser.accountId}/deposit?amount=${amt}`);
    renderMyAccount(d);
    document.getElementById('my-dep-amt').value = '';
    document.getElementById('res-my-deposit').innerHTML = `
      <div style="background:#f0fdf4;border:1px solid #a7f3d0;border-radius:10px;padding:12px 14px;font-family:'IBM Plex Mono',monospace;font-size:.8rem;color:#065f46;">
        ✅ Deposited ₹${fmtINR(amt)} · New balance: ₹${fmtINR(d.balance)}
      </div>`;
    addTxn('deposit', 'Deposit — ' + d.accountHolderName, parseFloat(amt), d.id);
    toast('✓ Deposit successful', 'ok');
    setTimeout(() => (document.getElementById('res-my-deposit').innerHTML = ''), 4000);
  } catch (e) {
    document.getElementById('res-my-deposit').innerHTML =
      `<div style="background:#fef2f2;border:1px solid #fecaca;border-radius:10px;padding:12px 14px;font-size:.82rem;color:#dc2626;">${e.message}</div>`;
  }
}

async function myWithdraw() {
  const amt = document.getElementById('my-wth-amt').value.trim();
  if (!amt || !currentUser?.accountId) { toast('Enter amount', 'err'); return; }
  try {
    const d = await api('PUT', `${BASE}/${currentUser.accountId}/withdraw?amount=${amt}`);
    renderMyAccount(d);
    document.getElementById('my-wth-amt').value = '';
    document.getElementById('res-my-withdraw').innerHTML = `
      <div style="background:#f0fdf4;border:1px solid #a7f3d0;border-radius:10px;padding:12px 14px;font-family:'IBM Plex Mono',monospace;font-size:.8rem;color:#065f46;">
        ✅ Withdrawn ₹${fmtINR(amt)} · New balance: ₹${fmtINR(d.balance)}
      </div>`;
    addTxn('withdraw', 'Withdrawal — ' + d.accountHolderName, parseFloat(amt), d.id);
    toast('✓ Withdrawal successful', 'ok');
    setTimeout(() => (document.getElementById('res-my-withdraw').innerHTML = ''), 4000);
  } catch (e) {
    document.getElementById('res-my-withdraw').innerHTML =
      `<div style="background:#fef2f2;border:1px solid #fecaca;border-radius:10px;padding:12px 14px;font-size:.82rem;color:#dc2626;">${e.message}</div>`;
  }
}
