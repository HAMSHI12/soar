'use client';

import { useState, useEffect } from 'react';
import EventTable from '../components/EventTable';
import ActionButtons from '../components/ActionButtons';
import api from '../lib/api';
import { ActionResponse } from '../types';

export default function Home() {
  const [events, setEvents] = useState<ActionResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchEvents = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await api.get('/events/process');
      setEvents(response.data);
    } catch (err: any) {
      setError('Failed to fetch events: ' + (err.response?.data || err.message));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEvents();
  }, []);

  return (
    <main className="w-full p-6">
      <h1 className="text-3xl font-bold mb-6">CyberSec Dashboard</h1>
      <ActionButtons onActionComplete={fetchEvents} />
      {error && <p className="text-red-500 mb-4">{error}</p>}
      <EventTable  events={events} loading={loading} />
    </main>
  );
}